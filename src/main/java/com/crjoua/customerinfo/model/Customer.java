package com.crjoua.customerinfo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
/**
 * Entity that represents a Customer
 */
@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 10)
    private String name;

    @Column(length = 100)
    private String address;

    @Column(length = 50)
    private String district;

    public Customer() {
        // empty constructor
    }

    public Customer(String name, String address, String district) {
        this.name = name;
        this.address = address;
        this.district = district;
    }

}
