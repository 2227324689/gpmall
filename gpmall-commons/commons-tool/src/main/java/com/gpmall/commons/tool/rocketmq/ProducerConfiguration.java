/*
package com.gpmall.commons.tool.rocketmq;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

*/
/**
 *
 *//*

@Configuration
public class ProducerConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerConfiguration.class);

    private final String PRODUCER_GROUP = "quartz-job-producer";
    private final String ROCKETMQ_NAMESERVER_ADDRESS = "127.0.0.1:9876";

    */
/**
     * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
     *//*

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    */
/**
     * 消息最大大小，默认4M
     *//*

    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize ;
    */
/**
     * 消息发送超时时间，默认3秒
     *//*

    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;
    */
/**
     * 消息发送失败重试次数，默认2次
     *//*

    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    @Bean
    public MQProducer mqProducer(){
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("mqProducer init");
        }

        String producerGroup;
        String rocketMqNameServerAddress;
        if (StringUtils.isNotEmpty(groupName)) {
            producerGroup = this.groupName;
        } else {
            producerGroup = PRODUCER_GROUP;
            if (LOGGER.isInfoEnabled()) {
                LOGGER.error("当前MQProducer生产者，没有配置producer.group属性，系统默认使用初始值。");
            }
        }

        if (StringUtils.isNotEmpty(namesrvAddr)) {
            rocketMqNameServerAddress = this.namesrvAddr;
        } else {
            rocketMqNameServerAddress = ROCKETMQ_NAMESERVER_ADDRESS;
            if (LOGGER.isInfoEnabled()) {
                LOGGER.error("当前MQProducer生产者，没有配置rocketmq.nameserver.address属性，系统默认使用初始值。");
            }
        }

        DefaultMQProducer producer;
        producer = new DefaultMQProducer(producerGroup);

        producer.setNamesrvAddr(rocketMqNameServerAddress);
        producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");

        if(this.maxMessageSize != null){
            producer.setMaxMessageSize(this.maxMessageSize);
        }
        if(this.sendMsgTimeout != null){
            //设置超时时间 默认是3000
            producer.setSendMsgTimeout(this.sendMsgTimeout);
        }
        //如果发送消息失败，设置重试次数，默认为2次
        if(this.retryTimesWhenSendFailed != null){
            producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
        }

        try {
            producer.start();
            LOGGER.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]", this.groupName, this.namesrvAddr));
        } catch (MQClientException e) {
            LOGGER.error(String.format("producer is error {}", e.getMessage(),e));
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("RocketMQ failed to start :", e);
            }
        }
        return producer;
    }
}
*/
