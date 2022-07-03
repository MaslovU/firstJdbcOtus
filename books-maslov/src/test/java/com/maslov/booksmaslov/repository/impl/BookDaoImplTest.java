package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.repository.AuthorDao;
import com.maslov.booksmaslov.repository.BookDao;
import com.maslov.booksmaslov.repository.GenreDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@JdbcTest
@Import({BookDaoImpl.class, GenreDaoImpl.class, AuthorDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoImplTest {

    @Autowired
    BookDao bookDao;

    @Autowired
    GenreDao genreDao;

    @Autowired
    AuthorDao authorDao;

    @Test
    void getAllBook() {
        List<Book> books = new ArrayList<>();
        Book book = new Book(1, "any","gov","2022", "study");

        List<Book> list = bookDao.getAllBook();
        System.out.println("eeee");
    }

    @Test
    void getBookById() {
    }

    @Test
    void getBooksByName() {
    }

    @Test
    void createBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }

    private static class BookMapper implements RowMapper<Book> {

        private final AuthorDao authorDao;
        private final GenreDao genreDao;

        private BookMapper(AuthorDao authorDao, GenreDao genreDao) {
            this.authorDao = authorDao;
            this.genreDao = genreDao;
        }

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int authorId = Integer.parseInt(resultSet.getString("author_id"));
            String author = authorDao.getNameById(authorId).getName();
            String year = resultSet.getString("year_of_publishing");
            int genreId = Integer.parseInt(resultSet.getString("genre_id"));
            String genre = genreDao.getNameById(genreId).getName();
            return new Book(id, name, author, year, genre);
        }
    }
}