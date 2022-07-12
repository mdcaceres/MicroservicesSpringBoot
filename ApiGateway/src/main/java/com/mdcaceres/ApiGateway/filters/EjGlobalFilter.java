package com.mdcaceres.ApiGateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class EjGlobalFilter implements GlobalFilter, Ordered {
    private final Logger log = LoggerFactory.getLogger(EjGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("filtro pre");
        //modificando el request con mutate
        exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.add("token","123456"));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
             log.info("filtro post");

             //pasando el token del request al response como ejemplo
             Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token"))
                     .ifPresent(value -> {
                         exchange.getResponse().getHeaders().add("token", value);
                     });
             //modificar el response
             exchange.getResponse().getCookies().add("Color", ResponseCookie.from("Color","rojo").build());
             //exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
  }

    @Override
    public int getOrder() {
        return -1;
    }
}




