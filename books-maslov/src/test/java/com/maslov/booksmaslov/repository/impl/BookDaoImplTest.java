package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.domain.YearOfPublish;
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
    private static final String JAVA = "java";
    private static final String STUDYING = "study";
    private static final String TEST = "Test";
    private static final int INT_ID = 1;
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

        String name = book.getGenreId().getName();

        List<Author> authors = book.getAuthor();
        List<String> authorsName = new ArrayList<>();
        for (Author a: authors) {
            authorsName.add(a.getAuthorName());
        }

        BookModel model = BookModel.builder()
                .name(book.getName())
                .authors(String.valueOf(authorsName))
                .genre(book.getGenreId().getName())
                .year(book.getYearId().getDateOfPublish())
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

        val author = new Author(0, "Lafore");
        val authors = Collections.singletonList(author);
        val year = new YearOfPublish(0, "2021");
        val genre = new Genre(0, "labuda");
        val comment = new Comment(0, "Third");
        val comment2 = new Comment(0, "Five");
        var comments = List.of(comment, comment2);

        var book = new Book(0, TEST, genre, year, authors, comments);

        bookDao.createBook(book);

        assertThat(bookDao.getBooksByName(TEST).get(INDEX_OF_BOOK).getName()).isEqualTo(TEST);
    }

    @Test
    void updateBook() {

        Optional<Book> book = bookDao.updateBook(ID, TEST, TEST, ID);

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