package com.prog_web.trabalho_final.controllers;

import com.prog_web.trabalho_final.dtos.CategoryDto;
import com.prog_web.trabalho_final.dtos.FilmDto;
import com.prog_web.trabalho_final.models.CategoryModel;
import com.prog_web.trabalho_final.models.FilmModel;
import com.prog_web.trabalho_final.services.CategoryService;
import com.prog_web.trabalho_final.services.FilmService;
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
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> saveFilm(@RequestBody @Valid FilmDto filmDto) {
        if (filmService.existsByTitle(filmDto.getTitle())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Já existe um Filme com o título informado!");
        }

        Optional<CategoryModel> categoryModelOptional = categoryService.findById(filmDto.getIdCategory());

        FilmModel filmModel = new FilmModel();

        BeanUtils.copyProperties(filmDto, filmModel);
        filmModel.setCategoryModel(categoryModelOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(filmService.saveFilm(filmModel));
    }

    @GetMapping
    public ResponseEntity<List<FilmModel>> getAllFilms() {
        return ResponseEntity.status(HttpStatus.OK).body(filmService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFilmById(@PathVariable(value = "id") UUID id) {
        Optional<FilmModel> filmModelOptional = filmService.findById(id);

        if (filmModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(filmModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFilm(@PathVariable(value = "id") UUID id) {
        Optional<FilmModel> filmModelOptional = filmService.findById(id);

        if (filmModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado!");
        }

        filmService.deleteFilm(filmModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Filme deletado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFilm(@PathVariable(value = "id") UUID id, @RequestBody @Valid FilmDto filmDto) {
        Optional<FilmModel> filmModelOptional = filmService.findById(id);

        if (filmModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado!");
        }

        Optional<CategoryModel> categoryModelOptional = categoryService.findById(filmDto.getIdCategory());

        FilmModel filmModel = new FilmModel();
        BeanUtils.copyProperties(filmDto, filmModel);

        filmModel.setIdFilm(filmModelOptional.get().getIdFilm());
        filmModel.setCategoryModel(categoryModelOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(filmService.saveFilm(filmModel));
    }
}
