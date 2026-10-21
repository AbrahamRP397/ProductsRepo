package com.example.products.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data 
public class ProductDiscount {
    @NotNull 
    @Min (1)
    @Max (100)
    @PositiveOrZero Integer discount;
}
