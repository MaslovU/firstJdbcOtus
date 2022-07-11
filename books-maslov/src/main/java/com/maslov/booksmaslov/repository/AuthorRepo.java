package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepo extends JpaRepository<Author, Long> {

    List<Author> getByName(String name);

}
