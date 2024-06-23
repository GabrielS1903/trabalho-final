package com.prog_web.trabalho_final.dtos;

import com.prog_web.trabalho_final.models.CategoryModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class FilmDto {

    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    @Size(max = 1000)
    private String synopsis;
    @NotNull
    private Integer releaseYear;
    @NotNull
    private Integer duration;
    @NotBlank
    @Size(max = 100)
    private String language;
    @NotNull
    private UUID idCategory;

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

    public UUID getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(UUID idCategory) {
        this.idCategory = idCategory;
    }
}
