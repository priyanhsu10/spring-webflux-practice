package com.pro.playground.sec03;

import com.pro.playground.sec03.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec03")
public class CustomerServiceTest {
    private static  final Logger log= LoggerFactory.getLogger(CustomerServiceTest.class);

    @Autowired
    private WebTestClient client;
    @Test
    public  void  getAllCustomer(){
        this.client.get()
                .uri("/customer")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(CustomerDto.class)
                .value(x->log.info("{}",x))
                .hasSize(10);


    }
    @Test
    public  void  getPaginatedCustomer(){
        this.client.get()
                .uri("/customer/paginated")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(x->log.info("{}",x))
                .jsonPath("$.totalElements").isEqualTo(10);


    }
}
