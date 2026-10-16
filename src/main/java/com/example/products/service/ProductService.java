package com.example.products.service;

import com.example.products.model.Product;
import com.example.products.model.Category;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repo;
    private final CategoryRepository repoCategory;



    public ProductService(ProductRepository repo, CategoryRepository repoCategory) {
        this.repo = repo;
        this.repoCategory = repoCategory;
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    public Product save(Product product, Long categoryId) {
        Category category = repoCategory.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + categoryId));

        product.setCategory(category);
        return repo.save(product);
    }

    public Optional<Product> update(Long id, Product newProduct, Long categoryId) {
        Optional<Product> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return Optional.empty();
        }

        Category category = repoCategory.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + categoryId));

        Product productBD = optional.get();
        productBD.setName(newProduct.getName());
        productBD.setPrice(newProduct.getPrice());
        productBD.setStock(newProduct.getStock());
        productBD.setCategory(category);

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
