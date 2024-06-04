package com.prog_web.trabalho_final.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_CATEGORY")
public class CategoryModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCategory;
    @Column(nullable = false, length = 100, unique = true)
    private String name;
    @Column(nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'A'")
    private String status = "A";

    public UUID getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(UUID idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
