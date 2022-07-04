package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAllAuthors();

    Author getByName(String name);

    Author getAuthorById(int id);

    String getAuthorId(String name);

    int createAuthor(String name);
}
