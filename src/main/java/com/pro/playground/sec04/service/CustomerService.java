package com.pro.playground.sec04.service;


import com.pro.playground.sec04.dto.CustomerDto;
import com.pro.playground.sec04.mapper.EntityDtoMapper;
import com.pro.playground.sec04.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<Page<CustomerDto>> getCustomerBy(int page, int cout) {
        var pageRequest=PageRequest.of(page - 1, cout);PageRequest.of(page - 1, cout);
        return this.customerRepository.findBy(pageRequest)
                .map(EntityDtoMapper::toDto)
                .collectList()
                .zipWith(this.customerRepository.count())
                .map(t->new PageImpl<>(t.getT1(),pageRequest,t.getT2()));
    }

}