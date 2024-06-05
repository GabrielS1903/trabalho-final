package com.prog_web.trabalho_final.services;

import com.prog_web.trabalho_final.models.CategoryModel;
import com.prog_web.trabalho_final.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<CategoryModel> findById(UUID id) {
        return categoryRepository.findById(id);
    }

    public List<CategoryModel> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void deleteCategory(CategoryModel categoryModelOptional) {
        categoryRepository.delete(categoryModelOptional);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
