package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAllNames();

    String getNameById(int id);

    int createGenre(String name);

    String getAuthorId(String name);
}
