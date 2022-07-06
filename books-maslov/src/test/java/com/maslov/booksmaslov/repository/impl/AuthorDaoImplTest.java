package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.repository.AuthorDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoImplTest {

    public static final long ID = 1L;
    private static final String JAVA = "java";
    private static final String DINNER = "dinner";
    private static final Long EXPECTED_ID = 1L;
    @Autowired
    private AuthorDao dao;

    @Test
    void getAllNames() {
        List<Author> listAuthors = dao.getAllAuthors();

        assertThat(listAuthors.size()).isNotZero();
    }

    @Test
    void getByName() {
        Author author = dao.getByName(JAVA);

        assertThat(author.getName()).isEqualTo(JAVA);
    }

    @Test
    void getAuthorById() {
        Author author = dao.getAuthorById(ID).get();

        assertThat(author.getName()).isEqualTo(JAVA);
    }

    @Test
    void getAuthorId() {
        Long id = dao.getByName(JAVA).getId();

        assertThat(id).isEqualTo(EXPECTED_ID);
    }

    @Test
    void getAuthorIdIfAuthorIsNotExists() {
        Author author = dao.getByName(JAVA);

        assertThat(author.getName()).isEqualTo(JAVA);
    }

    @Test
    void createAuthor() {
        List<Author> allAuthors = dao.getAllAuthors();
        Collections.sort(allAuthors);
        Long expecredId = allAuthors.get(allAuthors.size() - 1).getId();
        Author author = new Author(null, DINNER);

        Author resAuthor = dao.createAuthor(author);

        assertThat(resAuthor.getName()).isEqualTo(DINNER);
    }
}