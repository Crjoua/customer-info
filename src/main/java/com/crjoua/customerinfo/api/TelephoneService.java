package com.crjoua.customerinfo.api;

import com.crjoua.customerinfo.model.Customer;
import com.crjoua.customerinfo.model.Telephone;
/**
 * interface for saving telephone
 */
public interface TelephoneService {
    /**
     * 
     * @param number
     * @param customer
     * @return the telephone created
     */
    Telephone create(String number, Customer customer);
}
