package com.pro.playground.sec04.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleException(CustomerNotFoundException ex){

        var problemDetail= ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
        problemDetail.setType(URI.create("http://exmaple.com/problem/customer-not-found"));
        problemDetail.setTitle("Customer Not Found");
        return  problemDetail;
    }
    @ExceptionHandler(InvalidInputException.class)
    public ProblemDetail handleException(InvalidInputException ex){

        var problemDetail= ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
        problemDetail.setType(URI.create("http://exmaple.com/problem/invalid-request"));
        problemDetail.setTitle("Invalid Input");
        return  problemDetail;
    }
}
