package com.crjoua.customerinfo.Impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crjoua.customerinfo.api.CustomerService;
import com.crjoua.customerinfo.api.TelephoneService;
import com.crjoua.customerinfo.model.Customer;
import com.crjoua.customerinfo.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository repository;

    @Autowired 
    private TelephoneService telephoneService;

    @Override
    public Customer create(String name, String address, String district, List<String> telephones) {
        this.validateName(name);
        Customer customer = new Customer(name, address, district);
        telephones.forEach(telephone -> this.telephoneService.create(telephone, customer));
        this.repository.save(customer);
        return customer;

    }

    private void validateName(String name) {
        if(StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Blank name!");
        }
        if(name.length() > 10) {
            throw new IllegalArgumentException("Name length should be less than 10.");
        }
        if(repository.findByName(name).size() > 0) {
            throw new RuntimeException("Name already exists.");
        }
    }
    
}
