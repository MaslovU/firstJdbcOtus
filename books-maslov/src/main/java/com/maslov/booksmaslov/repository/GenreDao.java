package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAllGenres();

    Genre getNameById(int id);

    Genre getByName(String name);

    int createGenre(String name);

    String getAuthorId(String name);
}
