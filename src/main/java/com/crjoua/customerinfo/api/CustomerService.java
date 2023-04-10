package com.crjoua.customerinfo.api;

import java.util.List;

import com.crjoua.customerinfo.model.Customer;

/**
 * interface for saving new Customers
 */
public interface CustomerService {
    /**
     * 
     * @param name
     * @param address
     * @param district
     * @param telephones
     * @return the customer created
     */
    Customer create(String name, String address, String district, List<String> telephones);
}
