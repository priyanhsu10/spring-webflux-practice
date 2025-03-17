package com.pro.playground.sec03.service;


import com.pro.playground.sec03.dto.CustomerDto;
import com.pro.playground.sec03.mapper.EntityDtoMapper;
import com.pro.playground.sec03.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Mono<CustomerDto> getCustomerById(int id) {
        return this.customerRepository.findById(id)
                .map(EntityDtoMapper::toDto);
    }

    public Flux<CustomerDto> getAllCustomer() {
        return this.customerRepository.findAll()
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> saveCustomer(Mono<CustomerDto> customerDtoMono) {
        return customerDtoMono
                .map(EntityDtoMapper::toEntity)
                .flatMap(x -> this.customerRepository.save(x))
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> updateCustomer(Mono<CustomerDto> customerDtoMono, int id) {

        return this.customerRepository.findById(id)
                .flatMap(x -> customerDtoMono)
                .map(EntityDtoMapper::toEntity)
                .doOnNext(x -> x.setId(id))
                .flatMap(x -> this.customerRepository.save(x))
                .map(EntityDtoMapper::toDto);

    }

    public Mono<Boolean> deleteCustomer(int id) {

        return this.customerRepository.deleteCustomerById(id);
    }

    public Mono<List<CustomerDto>> getCustomerBy(int page, int cout) {
        return this.customerRepository.findBy(PageRequest.of(page - 1, cout))
                .map(EntityDtoMapper::toDto)
                .collectList();
    }

}