package com.example.products.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
    }
}