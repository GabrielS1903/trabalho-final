package com.prog_web.trabalho_final.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_FILM")
public class FilmModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idFilm;
    @Column(nullable = false, length = 100, unique = true)
    private String title;
    @Column(nullable = false, length = 1000)
    private String synopsis;
    @Column(nullable = false)
    private Integer releaseYear;
    @Column(nullable = false)
    private Integer duration;
    @Column(nullable = false, length = 100)
    private String language;
    @ManyToOne
    @JoinColumn(nullable = false, name = "idCategory")
    private CategoryModel categoryModel;

    public UUID getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(UUID idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }
}
