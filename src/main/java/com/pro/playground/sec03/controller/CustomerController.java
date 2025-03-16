package com.pro.playground.sec03.controller;

import com.pro.playground.sec03.dto.CustomerDto;
import com.pro.playground.sec03.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<CustomerDto> getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDtoMono) {
        return this.customerService.saveCustomer(customerDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> updateCustomer(@RequestBody Mono<CustomerDto> customerDtoMono, @PathVariable int id) {
        return this.customerService.updateCustomer(customerDtoMono, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void>  deleteCustomer(@PathVariable int id) {
        return this.customerService.deleteCustomer(id);

    }


}
