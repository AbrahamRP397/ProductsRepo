package com.example.products.controller;

import com.example.products.dto.product.ProductRequest;
import com.example.products.dto.product.ProductResponse;
import com.example.products.model.Product;
import com.example.products.model.ProductDiscount;
import com.example.products.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> products = new ArrayList<>();
        for (Product product : service.findAll()) {
            products.add(toResponse(product));
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        Product product = service.findById(id);
        return ResponseEntity.ok(toResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product) {
        Product newProduct = service.save(toEntity(product), product.categoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest productUpdate) {
        Product product = service.update(id, toEntity(productUpdate), productUpdate.categoryId());
        return ResponseEntity.ok(toResponse(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/discount")
    public ResponseEntity<ProductResponse> discount(@PathVariable Long id, @Valid @RequestBody ProductDiscount discount) {
        Product product = service.discount(id, discount.getDiscount());
        return ResponseEntity.ok(toResponse(product));
    }

    private ProductResponse toResponse(Product product) {
        String categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
        Long categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                categoryName,
                categoryId
        );
    }
    private Product toEntity(ProductRequest product) {
        Product productEntity = new Product();
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setStock(product.stock());
        return productEntity;
    }
}
