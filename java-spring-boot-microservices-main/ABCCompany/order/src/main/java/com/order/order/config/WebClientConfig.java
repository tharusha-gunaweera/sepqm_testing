package com.order.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean(name = "inventoryWebClient")
    public WebClient inventoryWebClient(@LoadBalanced WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("http://inventory/api/v1").build();
    }

    @Bean(name = "productWebClient")
    public WebClient productWebClient(@LoadBalanced WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("http://product/api/v1").build();
    }
}






