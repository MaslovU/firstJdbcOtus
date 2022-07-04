package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.repository.GenreDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GenreDaoImplTest {

    private static final int ID = 3;
    private static final String ID_PYTHON = "5";
    private static final String NAME = "go";
    private static final String PYTHON = "python";
    private static final String JS = "JS";

    @Autowired
    private GenreDao dao;

    @Test
    void getAllNames() {
        List<Genre> list = dao.getAllGenres();

        assertThat(list.size()).isNotZero();
    }

    @Test
    void getNameById() {
        Genre genre = dao.getNameById(ID);

        assertThat(genre.getName()).isEqualTo("go");
    }

    @Test
    void getByName() {
        Genre genre = dao.getByName(NAME);

        assertThat(genre.getName()).isEqualTo(NAME);
    }

    @Test
    void getAuthorId() {
        String id = dao.getAuthorId(PYTHON);

        assertThat(id).isEqualTo(ID_PYTHON);
    }

    @Test
    void createGenre() {
        List<Genre> before = dao.getAllGenres();
        dao.createGenre(JS);
        List<Genre> after = dao.getAllGenres();

        assertThat(after).hasSize(before.size() + 1);
    }
}