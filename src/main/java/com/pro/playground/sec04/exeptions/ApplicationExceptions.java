package com.pro.playground.sec04.exeptions;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    public static <T> Mono<T> customerNotFound(int id) {

        return Mono.error(new CustomerNotFoundException(id));
    }

    public static <T> Mono<T> missingName() {

        return Mono.error(new InvalidInputException("Name is required"));

    }

    public static <T> Mono<T> missingValidEmail() {


        return Mono.error(new InvalidInputException("Valid is required"));
    }
}
