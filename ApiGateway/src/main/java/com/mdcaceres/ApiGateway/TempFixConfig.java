package com.mdcaceres.ApiGateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

@Configuration
public class TempFixConfig {
    @Bean
    public HttpClient webClient(){
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }
}
