package com.pro.playground.sec04.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;

@Order(1)
@Service
public class AuthenticationFilter implements WebFilter {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

   private  static final   Map<String ,String> userMap= Map.of(
           "test123","read",
           "test124","read:write"
   );
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var token= exchange.getRequest().getHeaders().getFirst("auth-token");
        if(token!=null &&userMap.containsKey(token)){
            exchange.getRequest().getAttributes().put("auth",userMap.get(token));
            return chain.filter(exchange);
        }
        else {
            return  Mono.fromRunnable(()->exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
        }
    }
}
