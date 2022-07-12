package com.maslov.booksmaslov.dao;

import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.repository.GenreRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GenreDao {
    @Autowired
    GenreRepo genreRepo;

    public Genre getGenreByName(String name) {
        return genreRepo.findByName(name);
    }

    public Genre  createGenre(Genre genre) {
        return genreRepo.save(genre);
    }
}
