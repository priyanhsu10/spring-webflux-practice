package com.pro.playground.sec04.exeptions;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Integer id) {
        super("Customer [id="+id+"] is not found");
    }
}
