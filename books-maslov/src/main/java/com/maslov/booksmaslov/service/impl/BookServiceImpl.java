package com.maslov.booksmaslov.service.impl;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.repository.BookDao;
import com.maslov.booksmaslov.service.BookService;
import com.maslov.booksmaslov.service.ScannerHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final String ENTER_ID = "Enter ID for book or 0 is your dont now ID";
    private final String GET_ALL = "Enter command 'getall' for search your book in list";

    private BookDao bookDao;
    private ScannerHelper helper;

    @Override
    public void getBook() {
        System.out.println(ENTER_ID);
        int id = helper.getIdFromUser();
        if (id > 0) {
            Book book = bookDao.getBookById(id);
            if (nonNull(book)) {
                System.out.println(book);
            } else {
                System.out.println("Book with this id is not exist");
            }
        } else {
            System.out.println(GET_ALL);
        }
    }

    @Override
    public void getAllBook() {
        List<Book> books = bookDao.getAllBook();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Override
    public void createBook() {
        System.out.println("Enter name of the book");
        String name = helper.getFromUser();
        System.out.println("Enter name of the author");
        String author = helper.getFromUser();
        System.out.println("Enter year of publish");
        String year = helper.getFromUser();
        System.out.println("Enter name of the genre");
        String genre = helper.getFromUser();
        bookDao.createBook(name, author, year, genre);
    }

    @Override
    public void updateBook() {
        System.out.println(ENTER_ID);
        int id = helper.getIdFromUser();
        if (id > 0) {
            System.out.println("Enter correct name of the book");
            String name = helper.getFromUser();
            System.out.println("Enter correct name of the author");
            String author = helper.getFromUser();
            System.out.println("Enter correct year of publish");
            String year = helper.getFromUser();
            System.out.println("Enter correct name of the genre");
            String genre = helper.getFromUser();
            log.info(bookDao.updateBook(id, name, author, year, genre).toString());
        } else {
            System.out.println(GET_ALL);
        }
    }

    @Override
    public void delBook() {
        System.out.println(ENTER_ID);
        int id = helper.getIdFromUser();
        if (id > 0) {
            bookDao.deleteBook(id);
            log.info("Book deleted successfully");
        } else {
            System.out.println(GET_ALL);
        }
    }
}
