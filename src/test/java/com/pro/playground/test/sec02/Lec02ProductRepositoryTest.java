package com.pro.playground.test.sec02;

import com.pro.playground.sec02.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Test
    public  void pagable(){
        Pageable p= PageRequest.of(0,3)
                .withSort(Sort.by("price").ascending());
        this.productRepository.findBy(p)
                .doOnNext(x->log.info("{}",x))
                .as(StepVerifier::create)
                .assertNext(x-> Assertions.assertEquals(200,x.getPrice()))
                .assertNext(x-> Assertions.assertEquals(250,x.getPrice()))
                .assertNext(x-> Assertions.assertEquals(300,x.getPrice()))
                .expectComplete()
                .verify();
    }
}
