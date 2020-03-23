package com.gpmall.user.registerVerification;

import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import java.util.Map;

/**
 * Administrator
 * 2019/8/22 0022
 * 18:31
 * 自定义的消费者工厂，也可以不要这个类
 */
public class KafKaRegisterSuccConsumerFactory extends DefaultKafkaConsumerFactory {
    public KafKaRegisterSuccConsumerFactory(Map confings) {
        super(confings);
    }
}
