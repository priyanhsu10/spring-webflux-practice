package com.pro.playground.test.sec02;

import com.pro.playground.sec02.entity.Customer;
import com.pro.playground.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec01CustomerRepositoryTest extends AbstractTest{
    private static final Logger log= LoggerFactory.getLogger(Lec01CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public  void findAll(){
       this.customerRepository.findAll()
               .doOnNext(c->log.info("{}",c))
               .as(StepVerifier::create)
               .expectNextCount(10)
               .expectComplete()
               .verify();
    }
    @Test
    public  void findById(){
        this.customerRepository.findById(2)
                .doOnNext(x->log.info("{}",x))
                .as(StepVerifier::create)
                .assertNext(x-> Assertions.assertEquals("mike",x.getName()))
                .expectComplete()
                .verify();
    }
    @Test
    public  void findByName(){
        this.customerRepository.findByName("jake")
                .doOnNext(x->log.info("{}",x))
                .as(StepVerifier::create)
                .assertNext(x->Assertions.assertEquals("jake@gmail.com",x.getEmail()))
                .expectComplete()
                .verify();
    }
    @Test
    public  void findByEmailEndingWith(){
        this.customerRepository.findByEmailEndingWith("ke@gmail.com")
                .doOnNext(x->log.info("{}",x))
                .as(StepVerifier::create)
                .assertNext(x->Assertions.assertEquals("mike@gmail.com",x.getEmail()))
                .assertNext(x->Assertions.assertEquals("jake@gmail.com",x.getEmail()))
                .expectComplete()
                .verify();
    }
    public void insertAndDeleteCustomer(){
        var customer = new Customer();
        customer.setEmail("marshal@gmail.com");
        customer.setName("marshal");
       this.customerRepository.save(customer)
               .doOnNext(x->log.info("{}",x))
               .as(StepVerifier::create)
               .assertNext(x->Assertions.assertNotNull(x.getId()))
               .expectComplete()
               .verify();
       this.customerRepository.count()
               .as(StepVerifier::create)
               .assertNext(x->Assertions.assertEquals(11L,x))
               .expectComplete()
               .verify();
       this.customerRepository.deleteById(11)
               .then(this.customerRepository.count())
               .as(StepVerifier::create)
               .expectNext(10L)
               .expectComplete()
               .verify();
    }
}
