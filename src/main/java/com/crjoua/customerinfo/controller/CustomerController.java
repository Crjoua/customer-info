package com.crjoua.customerinfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crjoua.customerinfo.api.CustomerService;
import com.crjoua.customerinfo.model.Customer;

/**
 * Controller that maps our createCustomer service
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    
    /**
     * 
     * @param name
     * @param address
     * @param district
     * @param telephones
     * @return Response 
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO dto) {
        Customer novoUsuario = this.customerService.create(dto.getName(), dto.getAddress(), dto.getDistrict(), dto.getTelephones());
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    } 
}
