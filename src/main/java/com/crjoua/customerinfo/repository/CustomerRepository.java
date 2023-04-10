package com.crjoua.customerinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crjoua.customerinfo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByName(String name);
}
