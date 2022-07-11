package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {

    List<Book> getBooksByName(String name);
}
