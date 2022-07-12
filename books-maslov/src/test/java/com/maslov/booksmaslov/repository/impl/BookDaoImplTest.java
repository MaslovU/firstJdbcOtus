package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.repository.AuthorDao;
import com.maslov.booksmaslov.repository.BookDao;
import com.maslov.booksmaslov.repository.GenreDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({BookDaoImpl.class, GenreDaoImpl.class, AuthorDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoImplTest {
    private static final int ID = 1;
    private static final String GO = "go";
    private static final int EXPECTED_ID = 2;
    private static final String TEST = "Test";
    private static final int INDEX_OF_BOOK = 0;
    @Autowired
    BookDao bookDao;

    @Autowired
    GenreDao genreDao;

    @Autowired
    AuthorDao authorDao;

    @Test
    void getAllBook() {

        List<Book> list = bookDao.getAllBook();

        assertThat(list.size()).isNotZero();
    }

    @Test
    void getBookById() {
        Book book = bookDao.getBookById(EXPECTED_ID);

        assertThat(book.getName()).isEqualTo(GO);
    }

    @Test
    void getBooksByName() {
        List<Book> books = bookDao.getBooksByName(GO);

        assertThat(books.get(0).getId()).isEqualTo(EXPECTED_ID);
    }

    @Test
    void createBook() {
        String name = TEST;
        bookDao.createBook(name, TEST, TEST, TEST);

        assertThat(bookDao.getBooksByName(name).get(INDEX_OF_BOOK).getName()).isEqualTo(name);
    }

    @Test
    void updateBook() {
        int id = ID;
        String name = TEST;
        String author = TEST;
        String year = TEST;
        String genre = TEST;

        Book book = bookDao.updateBook(id, name, author, year, genre);

        assertThat(book.getName()).isEqualTo(name);
    }

    @Test
    void deleteBook() {
        List<Book> booksBefore = bookDao.getAllBook();
        bookDao.deleteBook(ID);
        List<Book> booksAfter = bookDao.getAllBook();

        assertThat(booksAfter).hasSize(booksBefore.size() - 1);
    }
}