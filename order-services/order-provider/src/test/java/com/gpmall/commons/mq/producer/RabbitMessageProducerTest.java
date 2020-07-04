package com.gpmall.commons.mq.producer;

import com.gpmall.order.bootstrap.OrderProviderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 孤傲然
 * @description : rabbitmq 测试发送消息
 * @date :2020/1/14 18:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderProviderApplication.class)
public class RabbitMessageProducerTest {
    @Autowired
    private RabbitMessageProducer rabbitMessageProducer;
    @Test
    public void send() {
        rabbitMessageProducer.send("20011411470626160");
    }
}
