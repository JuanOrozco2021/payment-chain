/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.customer.services;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author juaoroga
 */
@Service
public class CustomerService {
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    public String getProductName(Long id) {
        WebClient build = webClientBuilder
                .baseUrl("http://localhost:8082/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();

        return block.get("name").asText();
    }
    
        public List<?> getTransaction(String iban) {
        WebClient build = webClientBuilder
                .baseUrl("http://localhost:8184/transaction")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        List<?> transactions= build.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
        .path("/get-for-number")
        .queryParam("accountIban", iban)
        .build())
                .retrieve().bodyToFlux(Object.class).collectList().block();

        return transactions;
    }
    
}
