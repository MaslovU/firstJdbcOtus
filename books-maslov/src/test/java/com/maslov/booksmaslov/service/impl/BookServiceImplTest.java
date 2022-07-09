package com.maslov.booksmaslov.service.impl;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.domain.YearOfPublish;
import com.maslov.booksmaslov.repository.BookDao;
import com.maslov.booksmaslov.repository.impl.BookDaoImpl;
import com.maslov.booksmaslov.repository.impl.GenreDaoImpl;
import com.maslov.booksmaslov.service.BookService;
import com.maslov.booksmaslov.service.ScannerHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(BookDaoImpl.class)
@SpringJUnitConfig(BookServiceImpl.class)
class BookServiceImplTest {

    @MockBean
    private ScannerHelper scanner;
    @MockBean
    private BookDao bookDao;

    @Autowired
    BookService service;

    @Test
    void getBook() {

        when(scanner.getIdFromUser()).thenReturn(0);

        service.getBook();

        verify(bookDao, Mockito.times(0)).getBookById(1);

    }

    @Test
    void createBook() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(0, "Gorky"));
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(0, "Gorky"));
        Book book = new Book(0, "Gorky", new Genre(0, "Gorky"),
                new YearOfPublish(0, "Gorky"), authors, comments);

        when(scanner.getFromUser()).thenReturn("any()");
        when(scanner.getFromUser()).thenReturn("ex");
        when(scanner.getFromUser()).thenReturn("2020");
        when(scanner.getFromUser()).thenReturn("Gorky");

        service.createBook();

        verify(bookDao, Mockito.times(1)).createBook(book);
    }

    @Test
    void updateBook() {
        Book book = new Book(0, "as", new Genre(), new YearOfPublish(), new ArrayList<>(), new ArrayList<>());

        when(scanner.getIdFromUser()).thenReturn(1);
        when(scanner.getFromUser()).thenReturn("str");
        when(scanner.getFromUser()).thenReturn("str");
        when(scanner.getIdFromUser()).thenReturn(1);
        when(scanner.getIdFromUser()).thenReturn(1);
        when(bookDao.updateBook(anyInt(), anyString(), any(), anyInt()))
                .thenReturn(Optional.of(book));

        service.updateBook();

        verify(bookDao, Mockito.times(1))
                .updateBook(anyLong(), anyString(), any(), anyLong());
    }

    @Test
    void delBook() {
        when(scanner.getIdFromUser()).thenReturn(1);

        service.delBook();

        verify(bookDao, Mockito.times(1)).deleteBook(anyLong());
    }

    @Test
    void delBookWithZeroId() {
        when(scanner.getIdFromUser()).thenReturn(0);

        service.delBook();

        verify(bookDao, Mockito.times(0)).deleteBook(anyLong());
    }
}