package com.mq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.oracle.jrockit.jfr.Producer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by WD42700 on 2019/4/9.
 */
public class TestMq {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("mqtest_group");

        producer.setNamesrvAddr("172.20.100.121:9876");

        producer.start();

        String[] tags = new String[] { "TagA", "TagC", "TagD" };

        Date date = new Date();
        for (int i = 0; i < 10; i++) {
            // 加个时间后缀
            String body = String.valueOf(i*1000) ;
            Message msg = new Message("mq_order", null, "KEY" + i, body.getBytes());

            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    //arg其实就是第三个参数 、用于确定所在队列的
                    Long id = (Long) arg;
                    long index = id % mqs.size();
                    return mqs.get((int)index);
                }
            }, 3L);//订单id

            System.out.println(sendResult + ", body:" + body);
        }

        producer.shutdown();

    }



}
