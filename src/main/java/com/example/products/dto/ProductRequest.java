package com.example.products.dto;

public record ProductRequest(
        String name,
        Double price,
        int stock
) {

}
