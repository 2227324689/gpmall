package com.gpmall.user.kafkaConfig;

import com.gpmall.user.dal.entitys.User;
import com.gpmall.user.registerVerification.KafKaRegisterSuccConsumerFactory;
import com.gpmall.user.registerVerification.KafKaRegisterSuccProducer;
import com.gpmall.user.registerVerification.KafKaRegisterSuccProducerFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

/**
 * 咕泡学员
 * Administrator
 * 2019/8/23 0023
 * 14:30
 * 这种方式可以很灵活的添加多个生产者工厂；
 * 用户针对不同的业务（主要方便与序列化方式的不同的生产者 或者 如果能统一序列化的方式也可以使用springboot autoconfig 里面提供的默认的工厂和template）
 */
@Configuration
public class KafKaConfig {
    /**该类是在spring boot autoconfig 中就初始化了**/
    @Autowired
    KafkaProperties kafkaProperties;
    @Bean
    public KafKaRegisterSuccProducerFactory kafKaRegisterSuccProducerFactory(){
        Map<String,Object>  producerProperties = kafkaProperties.buildProducerProperties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaProperties.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);
        producerProperties.put(ProducerConfig.ACKS_CONFIG,"-1");
        return new KafKaRegisterSuccProducerFactory(producerProperties);
    }

    @Bean
    public KafkaTemplate registerSuccInfoTemplate(){
        KafkaTemplate template =  new KafkaTemplate<>(kafKaRegisterSuccProducerFactory());
        //template.setDefaultTopic("user-register-succ-topic");
        return template;
    }


    @Bean
    public KafKaRegisterSuccConsumerFactory kafKaRegisterSuccConsumerFactory(){
        Map<String,Object>  consumerProperties = kafkaProperties.buildProducerProperties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaProperties.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,"default");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);//消费者的自动提交方式关闭
        // consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,10);//在一个的轮训方法中，返回的最大记录数
        consumerProperties.put("spring.json.trusted.packages" ,"com.gpmall.user.dal.entitys,com.gpmall.user.dal.*");
        /*
        earliest:当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        latest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        none: topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        */
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        return new KafKaRegisterSuccConsumerFactory(consumerProperties);
    }

    /**
     * 消费则的监听工厂
     * @return
     */
    @Bean
    public KafkaListenerContainerFactory userRegisterSuccKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory conFactory = new ConcurrentKafkaListenerContainerFactory<>();
        conFactory.setConsumerFactory(kafKaRegisterSuccConsumerFactory());
        conFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);//  设置消费者消费消息后的提交方式为手动提交
        return conFactory;
    }
}
