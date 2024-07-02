package com.prog_web.trabalho_final.controllers;

import com.prog_web.trabalho_final.dtos.CategoryDto;
import com.prog_web.trabalho_final.models.CategoryModel;
import com.prog_web.trabalho_final.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test Category");

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setIdCategory(UUID.randomUUID());
        categoryModel.setName("Test Category");

        when(categoryService.existsByName(anyString())).thenReturn(false);
        when(categoryService.saveCategory(any(CategoryModel.class))).thenReturn(categoryModel);

        ResponseEntity<Object> response = categoryController.saveCategory(categoryDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryModel, response.getBody());
    }

    @Test
    void testGetAllCategories() {
        List<CategoryModel> categories = new ArrayList<>();
        CategoryModel category1 = new CategoryModel();
        category1.setIdCategory(UUID.randomUUID());
        category1.setName("Category 1");
        categories.add(category1);

        when(categoryService.findAll()).thenReturn(categories);

        ResponseEntity<List<CategoryModel>> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
    }

    @Test
    void testGetCategoryById() {
        UUID id = UUID.randomUUID();
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setIdCategory(id);
        categoryModel.setName("Category 1");

        when(categoryService.findById(id)).thenReturn(Optional.of(categoryModel));

        ResponseEntity<Object> response = categoryController.getCategoryById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryModel, response.getBody());
    }

    @Test
    void testDeleteCategory() {
        UUID id = UUID.randomUUID();
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setIdCategory(id);
        categoryModel.setName("Category 1");

        when(categoryService.findById(id)).thenReturn(Optional.of(categoryModel));
        doNothing().when(categoryService).deleteCategory(categoryModel);

        ResponseEntity<Object> response = categoryController.deleteCategory(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Categoria deletada com sucesso!", response.getBody());
    }

    @Test
    void testUpdateCategory() {
        UUID id = UUID.randomUUID();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Updated Category");

        CategoryModel existingCategoryModel = new CategoryModel();
        existingCategoryModel.setIdCategory(id);
        existingCategoryModel.setName("Existing Category");

        CategoryModel updatedCategoryModel = new CategoryModel();
        updatedCategoryModel.setIdCategory(id);
        updatedCategoryModel.setName("Updated Category");

        when(categoryService.findById(id)).thenReturn(Optional.of(existingCategoryModel));
        when(categoryService.saveCategory(any(CategoryModel.class))).thenReturn(updatedCategoryModel);

        ResponseEntity<Object> response = categoryController.updateCategory(id, categoryDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedCategoryModel, response.getBody());
    }
}

