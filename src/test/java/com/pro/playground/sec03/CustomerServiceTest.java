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
    @Test
    public  void  getCustomerByid(){
        this.client.get()
                .uri("/customer/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(x->log.info("{}",x))
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("sam")
                .jsonPath("$.email").isEqualTo("sam@gmail.com");


    }
    @Test
    public  void createCustomer(){
        var dot=new CustomerDto(null,"test","test@gmail.com");
        this.client.post()
                .uri("/customer")
                .bodyValue(dot)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(11)
                .jsonPath("$.name").isEqualTo("test")
                .jsonPath("$.email").isEqualTo("test@gmail.com");

    }
    @Test
    public  void updateCustomer(){
        var dto=new CustomerDto(null,"priyanshu","priyanshu@gmail.com");
        this.client
                .put()
                .uri("/customer/1")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("priyanshu")
                .jsonPath("$.email").isEqualTo("priyanshu@gmail.com");
    }

    @Test
    public  void notFoundTest(){

        this.client
                .get()
                .uri("/customer/11")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();
        var dto=new CustomerDto(null,"test","test@gmail.com");
        this.client
                .put()
                .uri("/customer/11")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();

        this.client.delete()
                .uri("/customer/11")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();

    }
}
