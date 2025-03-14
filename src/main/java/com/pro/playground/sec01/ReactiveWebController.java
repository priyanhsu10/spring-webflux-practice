package com.pro.playground.sec01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("reactive")
public class ReactiveWebController {
    private static final Logger log = LoggerFactory.getLogger(ReactiveWebController.class);
    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:7070").build();

    @GetMapping("product")
    public Flux<Product> getProducts() {

        return webClient.get().uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(x -> log.info("received : {}", x));

    }

    @GetMapping(value = "products2",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProducts2() {

        return webClient.get().uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(x -> log.info("received : {}", x));

    }
    @GetMapping(value = "products/notorious")
    public  Flux<Product> getnotorious(){
        return  webClient.get().uri("/demo01/products/notorious")
                .retrieve()
                .bodyToFlux(Product.class)
                .onErrorComplete()
                .doOnNext(x->log.info("recieved : {}",x));
    }
}
