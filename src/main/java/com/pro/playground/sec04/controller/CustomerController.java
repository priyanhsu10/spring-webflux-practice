package com.pro.playground.sec04.controller;

import com.pro.playground.sec04.dto.CustomerDto;
import com.pro.playground.sec04.exeptions.ApplicationExceptions;
import com.pro.playground.sec04.service.CustomerService;
import com.pro.playground.sec04.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
        return customerService.getCustomerById(id).switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDtoMono) {
        return customerDtoMono.transform(RequestValidator.validate)
                .as(this.customerService::saveCustomer);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> updateCustomer(@RequestBody Mono<CustomerDto> customerDtoMono, @PathVariable int id) {
        return customerDtoMono.transform(RequestValidator.validate)
                .as(x -> this.customerService.updateCustomer(x,id))
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable int id) {

        return this.customerService.deleteCustomer(id).
                filter(x -> x)
                .map(x -> ResponseEntity.ok().<Void>build())
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @GetMapping("/paginated")
    public Mono<Page<CustomerDto>> getCustomers(@RequestParam(value = "page", defaultValue = "1")
                                                int page,
                                                @RequestParam(value = "count", defaultValue = "3")
                                                int count) {
        return this.customerService.getCustomerBy(page, count);

    }

}
