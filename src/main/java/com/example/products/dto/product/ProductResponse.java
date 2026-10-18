package com.example.products.dto.product;

public record ProductResponse(
        Long id,
        String name,
        Double price,
        Integer stock,

        String categoryName,
        Long categoryId
) {

}
