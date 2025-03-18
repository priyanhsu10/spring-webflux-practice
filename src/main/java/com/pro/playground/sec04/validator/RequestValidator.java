package com.pro.playground.sec04.validator;

import com.pro.playground.sec04.dto.CustomerDto;
import com.pro.playground.sec04.exeptions.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class RequestValidator {
    public static  final Predicate<CustomerDto> hasName=dto-> Objects.nonNull(dto);
    private  static  final Predicate<CustomerDto> hasValidAddress= dto->Objects.nonNull(dto) && dto.email().contains("@");
    public  static  final Function<Mono<CustomerDto>,Mono<CustomerDto>> validate= mono->mono.filter(hasName)
            .switchIfEmpty(ApplicationExceptions.missingName())
            .filter(hasValidAddress)
            .switchIfEmpty(ApplicationExceptions.missingValidEmail());
}
