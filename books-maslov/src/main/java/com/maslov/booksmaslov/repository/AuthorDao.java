package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAllNames();

    Author getByName(String name);

    String getAuthorId(String name);

    int createAuthor(String name);
}
