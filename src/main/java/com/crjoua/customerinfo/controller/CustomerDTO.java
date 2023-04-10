package com.crjoua.customerinfo.controller;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class CustomerDTO implements Serializable {
    private String name;
    private String district;
    private String address;
    private List<String> telephones;
}
