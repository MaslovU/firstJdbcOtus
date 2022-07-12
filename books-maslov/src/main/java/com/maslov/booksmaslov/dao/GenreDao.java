package com.maslov.booksmaslov.dao;

import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.repository.GenreRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class GenreDao {
    private final GenreRepo genreRepo;

    public GenreDao(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    public Genre getGenreByName(String name) {
        return genreRepo.findByName(name);
    }

    public Genre createGenre(Genre genre) {
        return genreRepo.save(genre);
    }
}
