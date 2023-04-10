package com.crjoua.customerinfo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.crjoua.customerinfo.api.CustomerService;

@SpringBootTest
class CustomerInfoApplicationTests {

	@Autowired
	private CustomerService customerService;

	@Test
	void contextLoads() {
		assertNotNull(customerService);
	}

}
