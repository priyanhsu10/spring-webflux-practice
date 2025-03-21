package com.pro.playground.sec04.filters;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HexFormat;

@Component
@Order(2)

public class AuthorizationWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Object auth = exchange.getRequest().getAttributes().get("auth");
        String[] grants = auth.toString().split(":");
        HttpMethod method = exchange.getRequest().getMethod();
        if (method.equals(HttpMethod.GET) && Arrays.stream(grants).anyMatch(x -> x.equals("read"))) {
            return chain.filter(exchange);
        }
        var writeMethod=method.equals(HttpMethod.POST) ||method.equals(HttpMethod.PUT) || method.equals(HttpMethod.DELETE);
         if(writeMethod && Arrays.stream(grants).anyMatch(x->x.equals("write"))){
          return  chain.filter(exchange) ;

        }
         return  Mono.fromRunnable(()->exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));

    }
}


