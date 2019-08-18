package com.gpmall.pay.bootstrap;

import com.gpmall.pay.dal.entitys.Payment;
import com.gpmall.pay.dal.persistence.PaymentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayProviderApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    PaymentMapper paymentMapper;
    @Test
    public void tkMapperTest(){
        Payment payment = paymentMapper.selectByPrimaryKey("77451a4f-9283-47a7-9e65-715d361fdbe1");
        System.out.println("payment = " + payment);
    }
}
