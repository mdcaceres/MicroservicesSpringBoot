package com.mdcaceres.ApiGateway.filters.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class EjGatewayFilterFactory extends AbstractGatewayFilterFactory<FilterConfig> {
    private final Logger logger = LoggerFactory.getLogger(EjGatewayFilterFactory.class);

    public EjGatewayFilterFactory(){
        super(FilterConfig.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("mensaje", "cookieNombre", "cookieValor");
    }

    @Override
    public GatewayFilter apply(FilterConfig config) {
        return (exchange, chain) -> {
            logger.info("ejecutando pre abstract filter factory: " + config.getMensaje());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                Optional.ofNullable(config.getCookieValor()).ifPresent(cookie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.getCookieNombre(), cookie).build());
                });
                logger.info("ejecutando post abstract filter factory: " + config.getMensaje());
            }));
        };
    }
}
