package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.repository.AuthorDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.xmlunit.validation.JAXPValidator;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoImplTest {

    public static final String NAME = "lafore";
    public static final int ID = 1;
    private static final String JAVA = "java";
    private static final String DINNER = "dinner";
    private static final String EXPECTED_ID = "3";
    @Autowired
    private AuthorDao dao;

    @Test
    void getAllNames() {
        List<Author> listAuthors = dao.getAllAuthors();

        assertThat(listAuthors.size()).isNotZero();
    }

    @Test
    void getByName() {
        Author author = dao.getByName(NAME);

        assertThat(author.getName()).isEqualTo(NAME);
    }

    @Test
    void getAuthorById() {
        Author author = dao.getAuthorById(ID);

        assertThat(author.getName()).isEqualTo(NAME);
    }

    @Test
    void getAuthorId() {
        String id = dao.getAuthorId(JAVA);

        assertThat(id).isEqualTo(EXPECTED_ID);
    }

    @Test
    void getAuthorIdIfAuthorIsNotExists() {
        dao.getAuthorId(DINNER);
        Author author = dao.getByName(DINNER);

        assertThat(author.getName()).isEqualTo(DINNER);
    }

    @Test
    void createAuthor() {
        List<Author> allAuthors = dao.getAllAuthors();
        Collections.sort(allAuthors);
        int expecredId = allAuthors.get(allAuthors.size() -1).getId();

        int id = dao.createAuthor(DINNER);

        assertThat(id).isEqualTo(expecredId + 1);
    }
}