package com.example.products.service;

import com.example.products.exception.ResourceNotFoundException;
import com.example.products.exception.StockException;
import com.example.products.model.Product;
import com.example.products.model.Category;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Product findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }

    public Product save(Product product, Long categoryId) {
        Category category = repoCategory.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + categoryId));

        product.setCategory(category);
        return repo.save(product);
    }

    public Product update(Long id, Product newProduct, Long categoryId) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));

        Category category = repoCategory.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + categoryId));

        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        product.setStock(newProduct.getStock());
        product.setCategory(category);

        return repo.save(product);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado: " + id);
        }
        repo.deleteById(id);
    }

    public Product discount(Long id, Integer discount) {

        Product product = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id)); 

        if (product.getStock() == 0) {
           throw new StockException("El producto tiene un stock de 0 unidades");
        }
        
        product.setPrice(product.getPrice() * discount / 100);
    
        return repo.save(product);
    }
}
