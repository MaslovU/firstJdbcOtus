package com.maslov.booksmaslov.dao;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.domain.YearOfPublish;
import com.maslov.booksmaslov.repository.YearRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Year;

@Repository
@RequiredArgsConstructor
@Slf4j
public class YearDao {

    @Autowired
    YearRepo yearRepo;

    public YearDao(YearRepo yearRepo) {
        this.yearRepo = yearRepo;
    }

    public YearOfPublish getYearByDate(String year) {
        return yearRepo.findByDateOfPublish(year);
    }

    public YearOfPublish createYear(YearOfPublish year) {
        return yearRepo.save(year);
    }
}
