package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAllBook();

    Book getBookById(int id);

    List<Book> getBooksByName(String name);

    void createBook(String name, String author, String year, String genre);

    void deleteBook(Long id);

    Book updateBook(int id, String name, String author, String year, String genre);
}
