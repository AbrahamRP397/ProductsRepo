package com.example.products.service;

import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    public Product save(Product product) {
        return repo.save(product);
    }

    public Optional<Product> update(Long id, Product newProduct) {
        Optional<Product> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return Optional.empty();
        }

        Product productBD = optional.get();
        productBD.setName(newProduct.getName());
        productBD.setPrice(newProduct.getPrice());
        productBD.setStock(newProduct.getStock());

        return Optional.of(repo.save(productBD));
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }

        repo.deleteById(id);
        return true;
    }
}
