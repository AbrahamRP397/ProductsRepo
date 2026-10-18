package com.example.products.service;

import com.example.products.exception.ResourceNotFoundException;
import com.example.products.model.Category;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repo;
    private final ProductRepository productRepo;

    public CategoryService(CategoryRepository repo, ProductRepository productRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
    }

    public List<Category> findAll() {
        return repo.findAll();
    }

    public Category findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + id));
    }

    @Transactional
    public Category save(Category category) {
        return repo.save(category);
    }

    @Transactional
    public Category update(Long id, Category newCategory) {
        Category category = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + id));

        category.setName(newCategory.getName());
        return repo.save(category);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada: " + id);
        }
        if (productRepo.existsByCategoryId(id)) {
            throw new ResourceNotFoundException(
                    "No se puede eliminar la categoría " + id + ": tiene productos asociados");
        }
        repo.deleteById(id);
    }
}