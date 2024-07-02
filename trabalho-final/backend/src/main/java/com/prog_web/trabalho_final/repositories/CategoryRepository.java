package com.prog_web.trabalho_final.repositories;

import com.prog_web.trabalho_final.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
