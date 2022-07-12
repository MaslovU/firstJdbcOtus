package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Genre;
import liquibase.pro.packaged.L;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre, Long> {
    public Genre findByName(String name);
}
