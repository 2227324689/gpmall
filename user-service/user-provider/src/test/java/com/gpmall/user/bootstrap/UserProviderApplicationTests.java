package com.gpmall.user.bootstrap;

import com.gpmall.user.dal.entitys.Address;
import com.gpmall.user.dal.persistence.AddressMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProviderApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	AddressMapper addressMapper;
	@Test
	public void  test1(){
		Address address = addressMapper.selectByPrimaryKey(1L);
		System.out.println("address = " + address);
	}

}
