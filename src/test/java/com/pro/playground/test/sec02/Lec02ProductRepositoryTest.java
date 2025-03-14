package com.pro.playground.test.sec02;

import com.pro.playground.sec02.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;


public class Lec02ProductRepositoryTest extends  AbstractTest{
    private  static final Logger log= LoggerFactory.getLogger(Lec01CustomerRepositoryTest.class);
    @Autowired
   private ProductRepository productRepository ;
    @Test
    public void findProductByPriceBetween(){

        this.productRepository.findByPriceBetween(250,1000)
                .doOnNext(x->log.info("{}",x))
                .as(StepVerifier::create)
                .expectNextCount(6)
                .expectComplete()
                .verify();
    }
}
