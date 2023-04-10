package com.crjoua.customerinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crjoua.customerinfo.model.Telephone;

public interface TelephoneRepository extends JpaRepository<Telephone, Long>{
    List<Telephone> findByNumber(String number);
}
