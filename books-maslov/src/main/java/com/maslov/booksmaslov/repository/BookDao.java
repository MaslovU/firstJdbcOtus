package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Book;

import java.math.BigInteger;
import java.util.List;

public interface BookDao {
    List<Book> getAllBook();

    Book getBookById(int id);

    void createBook(int id, String name, String author, String year, String genre);

    Book updateBook(Long id);

    void deleteBook(Long id);
}
