package com.maslov.booksmaslov.service.impl;

import com.maslov.booksmaslov.dao.BookDao;
import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.service.BookService;
import com.maslov.booksmaslov.service.BookServiceHelper;
import com.maslov.booksmaslov.service.ScannerHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final String ENTER_ID = "Enter ID for book or 0 is your dont now ID";
    private final String GET_ALL = "Enter command 'getall' for search your book in list";

    private final BookDao bookDao;
    private final ScannerHelper helper;
    private final BookServiceHelper bookServiceHelper;

    public BookServiceImpl(BookDao bookDao, ScannerHelper helper, BookServiceHelper bookServiceHelper) {
        this.bookDao = bookDao;
        this.helper = helper;
        this.bookServiceHelper = bookServiceHelper;
    }

    @Override
    public void getBook() {
        System.out.println(ENTER_ID);
        int id = helper.getIdFromUser();
        if (id > 0) {
            Book book = bookDao.getBookById(id).get();
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
    public Book createBook() {
        Book bookFromUser = bookServiceHelper.getBookFromUser(0);
        return bookDao.createBook(bookFromUser);
    }

    @Override
    public void updateBook() {
        log.debug("Start updating book. if you don't want to change the value, click Enter");
        System.out.println(ENTER_ID);
        long id = helper.getIdFromUser();
        helper.getEmptyString();
        if (id > 0) {
            Book bookFromDB = bookDao.getBookById(id).get();
            bookDao.updateBook(bookServiceHelper.getBookFromUser(id), bookFromDB);
        } else {
            System.out.println(GET_ALL);
        }
    }

    @Override
    public void delBook() {
        System.out.println(ENTER_ID);
        long id = helper.getIdFromUser();
        if (id > 0) {
            bookDao.deleteBook(id);
            log.info("Book deleted successfully");
        } else {
            System.out.println(GET_ALL);
        }
    }
}
