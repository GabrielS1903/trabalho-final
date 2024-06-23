package com.prog_web.trabalho_final.services;

import com.prog_web.trabalho_final.models.FilmModel;
import com.prog_web.trabalho_final.repositories.FilmRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public boolean existsByTitle(String title) {
        return filmRepository.existsByTitleIgnoreCase(title);
    }

    @Transactional
    public Object saveFilm(FilmModel filmModel) {
        return filmRepository.save(filmModel);
    }

    public List<FilmModel> findAll() {
        return filmRepository.findAll();
    }

    public Optional<FilmModel> findById(UUID id) {
        return filmRepository.findById(id);
    }

    @Transactional
    public void deleteFilm(FilmModel filmModel) {
        filmRepository.delete(filmModel);
    }
}
