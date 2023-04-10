package com.crjoua.customerinfo.Impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crjoua.customerinfo.api.CustomerService;
import com.crjoua.customerinfo.model.Customer;
import com.crjoua.customerinfo.model.Telephone;
import com.crjoua.customerinfo.repository.CustomerRepository;
import com.crjoua.customerinfo.repository.TelephoneRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired 
    private TelephoneRepository telephoneRepository;
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNameBlank() {
        this.customerService.create("", "bla", "bla", new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNameNull() {
        this.customerService.create(null, "bla","bla", new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNameTooBig() {
        this.customerService.create("nametoobighere", "bla","bla", new ArrayList<>());
    }

    @Test(expected = RuntimeException.class)
    public void testCreateWithNameAlreadyExists() {
        this.customerService.create("name", "bla","bla", new ArrayList<>());
        customerRepository.flush();
        this.customerService.create("name", "bla1","bla2", new ArrayList<>());
    }

    @Test
    public void testCreateWithNameAndOneTelephone() {
        List<String> tels = new ArrayList<>();
        tels.add("12 999999999");
        this.customerService.create("name", "address","district", tels);
        customerRepository.flush();
        
        List<Customer> customersFound = this.customerRepository.findByName("name");
        Assert.assertEquals(1, customersFound.size());
        Customer customerCreated = customersFound.get(0);
        Assert.assertEquals(customerCreated.getName(), "name");
        Assert.assertEquals(customerCreated.getAddress(), "address");
        Assert.assertEquals(customerCreated.getDistrict(), "district");

        List<Telephone> telsFound = this.telephoneRepository.findAll();
        Assert.assertEquals(1, customersFound.size());
        Telephone telCreated = telsFound.get(0);
        Assert.assertEquals("12 999999999", telCreated.getNumber());
        Assert.assertEquals("name", telCreated.getCustomer().getName());
    }

    //
    @Test(expected = RuntimeException.class)
    public void testCreateWithOtherWithSameTelephone() {
        List<String> tels = new ArrayList<>();
        tels.add("12 999999999");
        this.customerService.create("name", "address","district", tels);
        customerRepository.flush();
        
        List<Customer> customersFound = this.customerRepository.findByName("name");
        Assert.assertEquals(1, customersFound.size());
        Customer customerCreated = customersFound.get(0);
        Assert.assertEquals(customerCreated.getName(), "name");
        Assert.assertEquals(customerCreated.getAddress(), "address");
        Assert.assertEquals(customerCreated.getDistrict(), "district");

        List<Telephone> telsFound = this.telephoneRepository.findAll();
        Assert.assertEquals(1, customersFound.size());
        Telephone telCreated = telsFound.get(0);
        Assert.assertEquals("12 999999999", telCreated.getNumber());

        //trow
        this.customerService.create("other", "address","district", tels);
    }

    /** invalid pattern*/
    @Test(expected = IllegalArgumentException.class)
    public void testCreateUnformatedTelephone() {
        List<String> tels = new ArrayList<>();
        tels.add("12 99999999a");
        this.customerService.create("name", "address","district", tels);
    }

}
