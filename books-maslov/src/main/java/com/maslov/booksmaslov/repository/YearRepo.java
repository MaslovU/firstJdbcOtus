package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.domain.YearOfPublish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YearRepo extends JpaRepository<YearOfPublish, Long> {
    public YearOfPublish findByDateOfPublish(String year);
}
