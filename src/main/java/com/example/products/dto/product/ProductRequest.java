package com.example.products.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
        @NotBlank String name,
        @NotNull @Positive Double price,
        @PositiveOrZero Integer stock,
        @NotNull Long categoryId
) {

}
