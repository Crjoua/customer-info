package com.crjoua.customerinfo.Impl;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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

/**
 * Teste de integração para salvar cliente
 * os testes englobam os casos de erro e de sucesso
 */
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
    
    /**
     * limpa as tabelas antes de cada teste
     */
    @Before
    public void cleanUp() {
        telephoneRepository.deleteAll();
        customerRepository.deleteAll();
    }

    /**
     * nome ""
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNameBlank() {
        this.customerService.create("", "bla", "bla", new ArrayList<>());
    }

    /**
     * nome null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNameNull() {
        this.customerService.create(null, "bla","bla", new ArrayList<>());
    }

    /**
     * nome maior que 10 chars
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNameTooBig() {
        this.customerService.create("nametoobighere", "bla","bla", new ArrayList<>());
    }

    /**
     * Erro ao criar cliente com nome já existente
     */
    @Test
    public void testCreateWithNameAlreadyExists() {
        this.customerService.create("name", "bla","bla", new ArrayList<>());
        customerRepository.flush();
        try {
            this.customerService.create("name", "bla1","bla2", new ArrayList<>());
            fail();
        } catch (RuntimeException e) {
            // empty block
        }
    }

    /**
     * cria um cliente com um numero de telefone valido e checa se ele está no banco com os valores corretos
     */
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

    /**
     * Erro ao tentar criar um outro cliente com um telefone que já está cadastrado
     */
    @Test
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

        try {
            this.customerService.create("other", "address","district", tels);
            fail();
        } catch (RuntimeException e) {
            // empty block
        }
        
    }

    /** invalid pattern*/
    @Test(expected = IllegalArgumentException.class)
    public void testCreateUnformatedTelephone() {
        List<String> tels = new ArrayList<>();
        tels.add("12 99999999a");
        this.customerService.create("name", "address","district", tels);
    }

}
