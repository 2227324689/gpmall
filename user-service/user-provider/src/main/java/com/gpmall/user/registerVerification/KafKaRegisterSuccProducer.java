package com.gpmall.user.registerVerification;

import com.gpmall.user.dal.entitys.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 咕泡学员
 * Administrator
 * 2019/8/22 0022
 * 18:31
 */
@Component
@Slf4j
public class KafKaRegisterSuccProducer {
    @Autowired
    @Qualifier("registerSuccInfoTemplate")
    KafkaTemplate kafkaTemplate ;

    private final static String topic = "user-register-succ-topic";

    public void sendRegisterSuccInfo(Map uerVerifyMap) {
        try {
            kafkaTemplate.send(topic, uerVerifyMap);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }
}
