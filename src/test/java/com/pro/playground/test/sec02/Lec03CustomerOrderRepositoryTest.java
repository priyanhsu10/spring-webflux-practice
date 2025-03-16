package com.pro.playground.test.sec02;

import com.pro.playground.sec02.repository.CustomerOrderRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec03CustomerOrderRepositoryTest extends AbstractTest{
    private static  final Logger log= LoggerFactory.getLogger(Lec03CustomerOrderRepositoryTest.class);

    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Test
    public  void productOrderBYCumstomerName(){
        this.customerOrderRepository
                .findOrderByCustomer("mike")
                .doOnNext(x->log.info("{}",x))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}
