package com.prog_web.trabalho_final.controllers;

import com.prog_web.trabalho_final.dtos.FilmDto;
import com.prog_web.trabalho_final.models.CategoryModel;
import com.prog_web.trabalho_final.models.FilmModel;
import com.prog_web.trabalho_final.services.CategoryService;
import com.prog_web.trabalho_final.services.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @MockBean
    private CategoryService categoryService;

    private FilmDto filmDto;
    private FilmModel filmModel;
    private CategoryModel categoryModel;
    private UUID categoryId;
    private UUID filmId;

    @BeforeEach
    public void setUp() {
        categoryId = UUID.randomUUID();
        filmId = UUID.randomUUID();

        filmDto = new FilmDto();
        filmDto.setTitle("Test Film");
        filmDto.setSynopsis("Test Synopsis");
        filmDto.setReleaseYear(2021);
        filmDto.setDuration(120);
        filmDto.setLanguage("English");
        filmDto.setIdCategory(categoryId);

        categoryModel = new CategoryModel();
        categoryModel.setIdCategory(categoryId);
        categoryModel.setName("Test Category");

        filmModel = new FilmModel();
        BeanUtils.copyProperties(filmDto, filmModel);
        filmModel.setCategoryModel(categoryModel);
        filmModel.setIdFilm(filmId);
    }

    @Test
    public void testSaveFilm() throws Exception {
        Mockito.when(categoryService.findById(categoryId)).thenReturn(Optional.of(categoryModel));
        Mockito.when(filmService.existsByTitle(filmDto.getTitle())).thenReturn(false);
        Mockito.when(filmService.saveFilm(Mockito.any(FilmModel.class))).thenReturn(filmModel);

        mockMvc.perform(post("/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test Film\", \"synopsis\": \"Test Synopsis\", \"releaseYear\": 2021, \"duration\": 120, \"language\": \"English\", \"idCategory\": \"" + categoryId + "\" }"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Test Film")));
    }

    @Test
    public void testGetAllFilms() throws Exception {
        mockMvc.perform(get("/film"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFilmById() throws Exception {
        Mockito.when(filmService.findById(filmId)).thenReturn(Optional.of(filmModel));

        mockMvc.perform(get("/film/" + filmId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Film")));
    }

    @Test
    public void testDeleteFilm() throws Exception {
        Mockito.when(filmService.findById(filmId)).thenReturn(Optional.of(filmModel));

        mockMvc.perform(delete("/film/" + filmId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Filme deletado com sucesso!")));
    }

    @Test
    public void testUpdateFilm() throws Exception {
        FilmDto updatedFilmDto = new FilmDto();
        updatedFilmDto.setTitle("Updated Film");
        updatedFilmDto.setSynopsis("Updated Synopsis");
        updatedFilmDto.setReleaseYear(2022);
        updatedFilmDto.setDuration(130);
        updatedFilmDto.setLanguage("French");
        updatedFilmDto.setIdCategory(categoryId);

        FilmModel updatedFilmModel = new FilmModel();
        BeanUtils.copyProperties(updatedFilmDto, updatedFilmModel);
        updatedFilmModel.setIdFilm(filmId);
        updatedFilmModel.setCategoryModel(categoryModel);

        Mockito.when(filmService.findById(filmId)).thenReturn(Optional.of(filmModel));
        Mockito.when(categoryService.findById(categoryId)).thenReturn(Optional.of(categoryModel));
        Mockito.when(filmService.saveFilm(Mockito.any(FilmModel.class))).thenReturn(updatedFilmModel);

        mockMvc.perform(put("/film/" + filmId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Updated Film\", \"synopsis\": \"Updated Synopsis\", \"releaseYear\": 2022, \"duration\": 130, \"language\": \"French\", \"idCategory\": \"" + categoryId + "\" }"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Updated Film")));
    }
}
