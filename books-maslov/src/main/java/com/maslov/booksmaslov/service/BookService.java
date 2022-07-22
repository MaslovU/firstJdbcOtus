package com.maslov.booksmaslov.service;

import com.maslov.booksmaslov.domain.Book;

public interface BookService {
    void getBook();

    void getAllBook();

    Book createBook();

    void updateBook();

    void delBook();
}
