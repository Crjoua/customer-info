package com.crjoua.customerinfo.Impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crjoua.customerinfo.api.TelephoneService;
import com.crjoua.customerinfo.model.Customer;
import com.crjoua.customerinfo.model.Telephone;
import com.crjoua.customerinfo.repository.TelephoneRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TelephoneServiceImpl implements TelephoneService {

    @Autowired
    private TelephoneRepository repository;

    @Override
    public Telephone create(String number, Customer customer) {
        this.validateTelephone(number);
        Telephone newTelephone = new Telephone(number, customer);
        return this.repository.save(newTelephone);
    }

    private void validateTelephone(String number) {
        String regexTelefone = "^[0-9]{2} [0-9]{9}$";
        if(StringUtils.isBlank(number)) {
            throw new IllegalArgumentException("Telephone number is blank!");
        }
        if(!number.matches(regexTelefone)) {
            throw new IllegalArgumentException("Telephone is not valid for pattern XX XXXXXXXXX.");
        }
        if(repository.findByNumber(number).size() > 0) {
            throw new RuntimeException("Telephone already exists!");
        }
    }
    
}
