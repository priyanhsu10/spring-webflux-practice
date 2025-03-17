package com.pro.playground.sec03.controller;

import com.pro.playground.sec03.dto.CustomerDto;
import com.pro.playground.sec03.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;


    @GetMapping
    public Flux<CustomerDto> getAll() {
        return this.customerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerDto>> getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id).
                map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDtoMono) {
        return this.customerService.saveCustomer(customerDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerDto>> updateCustomer(@RequestBody Mono<CustomerDto> customerDtoMono, @PathVariable int id) {
        return this.customerService.updateCustomer(customerDtoMono, id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable int id) {
        return this.customerService.deleteCustomer(id).
                filter(x -> x)
                .map(x -> ResponseEntity.ok().<Void>build())
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @GetMapping("/paginated")
    public Mono<List<CustomerDto>> getCustomers(@RequestParam(value = "page", defaultValue = "1")
                                                int page,
                                                @RequestParam(value = "count", defaultValue = "3")
                                                int count) {
        return  this.customerService.getCustomerBy(page,count);

    }

}
