package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.domain.Year;

import java.util.List;
import java.util.Optional;

public interface YearDao {
    List<Year> getAllYears();
    Year getYearByDate(String date);

    Optional<Year> getYearById(long id);

    Year createYear(Year year);
}
