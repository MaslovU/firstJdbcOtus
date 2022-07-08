package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.domain.Year;
import com.maslov.booksmaslov.model.BookModel;
import com.maslov.booksmaslov.repository.BookDao;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, YearDaoImpl.class, GenreDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoImplTest {
    private static final long ID = 1L;
    private static final String JAVA = "Java";
    private static final String STUDYING = "studying";
    private static final String TEST = "Test";
    private static final int INDEX_OF_BOOK = 0;
    @Autowired
    BookDao bookDao;

    @Autowired
    TestEntityManager em;

    @Test
    void getAllBook() {

        List<Book> list = bookDao.getAllBook();

        assertThat(list.size()).isNotZero();
    }

    @Test
    void getBookById() {
        Book book = bookDao.getBookById(ID).get();

        String name = book.getGenre().getName();

        List<Author> authors = book.getAuthor();
        List<String> authorsName = new ArrayList<>();
        for (Author a: authors) {
            authorsName.add(a.getName());
        }

        BookModel model = BookModel.builder()
                .name(book.getName())
                .authors(String.valueOf(authorsName))
                .genre(book.getGenre().getName())
                .year(book.getYearOfPublishing().getYear())
                .build();

        System.out.println(model);
        assertThat(book.getName()).isEqualTo(JAVA);
        assertThat(model.getGenre()).isEqualTo(STUDYING);
    }

    @Test
    void getBooksByName() {
        List<Book> books = bookDao.getBooksByName(JAVA);

        assertThat(books.get(0).getId()).isEqualTo(ID);
    }

    @Test
    void createBook() {

        Author author1 = new Author(null, "Labuda");
        val authors = Collections.singletonList(author1);
        Year year = new Year(null, "2020");
        Genre genre = new Genre(null, "labuda");

        var book = new Book(null, TEST, genre, year, authors);

        bookDao.createBook(book);

        assertThat(bookDao.getBooksByName(TEST).get(INDEX_OF_BOOK).getName()).isEqualTo(TEST);
    }

    @Test
    void updateBook() {

        Optional<Book> book = bookDao.updateBook(ID, TEST, TEST, TEST, TEST);

        assertThat(book.get().getName()).isEqualTo(TEST);
    }

    @Test
    void deleteBook() {
        List<Book> booksBefore = bookDao.getAllBook();
        bookDao.deleteBook(ID);
        List<Book> booksAfter = bookDao.getAllBook();

        assertThat(booksAfter).hasSize(booksBefore.size() - 1);
    }
}