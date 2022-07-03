package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.repository.GenreDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GenreDaoImplTest {

    @Autowired
    private GenreDao dao;

    @Test
    void getAllNames() {
        List<Genre> list = dao.getAllNames();
        System.out.print("eeeee");
    }

    @Test
    void getNameById() {
    }

    @Test
    void getByName() {
    }

    @Test
    void getAuthorId() {
    }

    @Test
    void createGenre() {
    }
}