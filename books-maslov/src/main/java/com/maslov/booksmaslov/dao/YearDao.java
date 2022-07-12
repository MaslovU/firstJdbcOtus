package com.maslov.booksmaslov.dao;

import com.maslov.booksmaslov.domain.YearOfPublish;
import com.maslov.booksmaslov.repository.YearRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class YearDao {

    private final YearRepo yearRepo;

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
