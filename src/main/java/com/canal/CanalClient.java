package com.canal;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.client.*;
import org.springframework.beans.factory.annotation.Autowired;


public class CanalClient {

    public static void main(String args[]) {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("106.13.2.16",
                11111), "example", "", "");
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            //connector.rollback();
            while (true) {
                /**
                 * 如果是第一次 fetch，则会从 canal 中保存的最老一条数据开始输出
                 * 在canal启动前的历史数据。该如何获取？
                 * 消息是否有顺序性问题。。
                 * 大部分java应用都是CMS垃圾收集器
                 * canal如何保存从mysql中收集到的数据
                 */
                //Message message = connector.getWithoutAck(batchSize,0L, TimeUnit.SECONDS);
                Message message = connector.getWithoutAck(batchSize);//不进行自动确认、需要手动确认
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    printEntry(message.getEntries());

                }
                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry( List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }
            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
            EventType eventType = rowChage.getEventType();
            System.err.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                rowData.getAfterColumnsList();
                printColumn(rowData);
                if (eventType == EventType.DELETE) {
                    //redisDelete(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    //redisInsert(rowData.getAfterColumnsList());
                } else {
                   // redisUpdate(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void printColumn(RowData row) {
        List<Column> beforeList = row.getBeforeColumnsList();
        List<Column> afterList = row.getAfterColumnsList();
        System.err.println("\n\n修改前的值");
        for (Column column : beforeList) {
            System.err.print(column.getName() + ":" + column.getValue()+"  ");
        }
        System.err.println("\n修改过的字段");
        for (Column column : afterList) {
            if(column.getUpdated() == true){
                System.err.println("名称 "+column.getName() + ":" + column.getValue() );
            }
        }

    }

    private static void redisInsert( List<Column> columns){
        JSONObject json=new JSONObject();
        for (Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        if(columns.size()>0){
            RedisUtil.stringSet("user:"+ columns.get(0).getValue(),json.toJSONString());
        }
    }

    private static  void redisUpdate( List<Column> columns){
        JSONObject json=new JSONObject();
        for (Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        if(columns.size()>0){
            RedisUtil.stringSet("user:"+ columns.get(0).getValue(),json.toJSONString());
        }
    }

    private static  void redisDelete( List<Column> columns){
        JSONObject json=new JSONObject();
        for (Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        if(columns.size()>0){
            RedisUtil.delKey("user:"+ columns.get(0).getValue());
        }
    }


}
