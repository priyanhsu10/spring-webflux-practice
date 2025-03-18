package com.pro.playground.sec04.exeptions;

public class CustomerNotFoundException extends RuntimeException {
    private  static final  String MESSAGE="Customer [id=%id] is not found";

    public CustomerNotFoundException(Integer id) {
        super(MESSAGE.formatted(id));
    }
}
