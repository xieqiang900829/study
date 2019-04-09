package com.mq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by WD42700 on 2019/4/9.
 */
public class Consumer1 {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_Consumer3");
        consumer.setNamesrvAddr("172.20.100.121:9876");

        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("mq_order", "*");
       // consumer.setConsumeThreadMin(20);
        //consumer.setConsumeThreadMax(20);

        int min = consumer.getConsumeThreadMin();//默认就是多线程的
        int max = consumer.getConsumeThreadMax();
        System.out.println("线程数统计 max= "+max +"       min="+min);//线程数统计 max= 64       min=20
        /**
         * 实现了MessageListenerOrderly表示一个队列只会被一个线程取到
         * 第二个线程无法访问这个队列
         */
        consumer.registerMessageListener(new MessageListenerOrderly() {
            AtomicLong consumeTimes = new AtomicLong(0);

            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                // 设置自动提交
                context.setAutoCommit(true);
                for (MessageExt msg : msgs) {
                    String s = new String(msg.getBody());
                    System.out.println(Thread.currentThread().getName()+" 内容：" + msg);
                    System.out.println(Thread.currentThread().getName()+" 消息体：" + s);
                    if (s.equals("3000")){
                      //  return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(3L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer1 Started.");
    }



}
