package com.maslov.booksmaslov.service.impl;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.domain.YearOfPublish;
import com.maslov.booksmaslov.repository.BookDao;
import com.maslov.booksmaslov.service.BookService;
import com.maslov.booksmaslov.service.ScannerHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
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
        Book book = new Book(0, "as", new Genre(), new YearOfPublish(), new ArrayList<>());

        when(scanner.getFromUser()).thenReturn("str");
        when(scanner.getFromUser()).thenReturn("str");
        when(scanner.getFromUser()).thenReturn("str");
        when(scanner.getFromUser()).thenReturn("str");

        service.createBook();

        verify(bookDao, Mockito.times(1)).createBook(book);
    }

    @Test
    void updateBook() {
        Book book = new Book(1L, "str", any(), any(), any());

        when(scanner.getIdFromUser()).thenReturn(1);
        when(scanner.getFromUser()).thenReturn("str");
        when(scanner.getFromUser()).thenReturn("str");
        when(scanner.getFromUser()).thenReturn("str");
        when(scanner.getFromUser()).thenReturn("str");
        when(bookDao.updateBook(anyInt(), anyString(), any(), any(), any()))
                .thenReturn(Optional.of(book));

        service.updateBook();

        verify(bookDao, Mockito.times(1)).updateBook(anyInt(), anyString(), any(), any(), any());
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