package com.pro.playground.sec04.mapper;

import com.pro.playground.sec04.dto.CustomerDto;
import com.pro.playground.sec04.entity.Customer;

public class EntityDtoMapper {
    public  static Customer toEntity(CustomerDto dto){
        var customer= new Customer();

        customer.setId(dto.id());
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        return  customer;
    }
    public  static CustomerDto toDto(Customer customer){
        return  new CustomerDto(customer.getId(),customer.getName(),customer.getEmail());
    }
}
