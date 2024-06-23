package com.prog_web.trabalho_final.controllers;

import com.prog_web.trabalho_final.dtos.CategoryDto;
import com.prog_web.trabalho_final.models.CategoryModel;
import com.prog_web.trabalho_final.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> saveCategory(@RequestBody @Valid CategoryDto categoryDto) {
        if (categoryService.existsByName(categoryDto.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Já existe uma Categoria com o nome informado!");
        }

        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryDto, categoryModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(categoryModel));
    }

    @GetMapping
    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable(value = "id") UUID id) {
        Optional<CategoryModel> categoryModelOptional = categoryService.findById(id);

        if (categoryModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(categoryModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") UUID id) {
        Optional<CategoryModel> categoryModelOptional = categoryService.findById(id);

        if (categoryModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada!");
        }

        categoryService.deleteCategory(categoryModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Categoria deletada com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") UUID id, @RequestBody @Valid CategoryDto categoryDto) {
        Optional<CategoryModel> categoryModelOptional = categoryService.findById(id);

        if (categoryModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada!");
        }

        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryDto, categoryModel);

        categoryModel.setIdCategory(categoryModelOptional.get().getIdCategory());

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(categoryModel));
    }

}
