package com.maslov.booksmaslov.service.impl;

import com.maslov.booksmaslov.dao.AuthorDao;
import com.maslov.booksmaslov.dao.BookDao;
import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.domain.YearOfPublish;
import com.maslov.booksmaslov.repository.AuthorRepo;
import com.maslov.booksmaslov.service.BookService;
import com.maslov.booksmaslov.service.ScannerHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final String ENTER_ID = "Enter ID for book or 0 is your dont now ID";
    private final String GET_ALL = "Enter command 'getall' for search your book in list";

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final ScannerHelper helper;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, ScannerHelper helper) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.helper = helper;
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
        return bookDao.createBook(getBookFromUser());
    }

    @Override
    public void updateBook() {
        log.debug("Start updating book");
        System.out.println(ENTER_ID);
        int id = helper.getIdFromUser();
        if (id > 0) {
            Book bookFromDB = bookDao.getBookById(id).get();
            bookDao.updateBook(getBookFromUser(), bookFromDB);
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

    private Book getBookFromUser() {
        System.out.println("Enter name of the book");
        String name = helper.getFromUser();
        System.out.println("Enter name of the author");
        String authorName = helper.getFromUser();
        long authorId = getAuthorId(authorName);
        Author author = Author.builder().id(authorId).name(authorName).build();
        val authors = Collections.singletonList(author);
        System.out.println("Enter years of publish");
        val year = YearOfPublish.builder().dateOfPublish(helper.getFromUser()).build();
        System.out.println("Enter name of the genre");
        val genre = Genre.builder().name(helper.getFromUser()).build();
        System.out.println("You can add comment to this book");
        val comment = Comment.builder().commentForBook(helper.getFromUser()).build();
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Book book = new Book();
        book.setAuthor(authors);
        book.setName(name);
        book.setGenre(genre);
        book.setListOfComment(comments);
        book.setYear(year);
        return book;
    }

    private long getAuthorId(String authorName) {
        if (authorDao.findAuthorByText(authorName).isEmpty()) {
            Author author = authorDao.createAuthor(Author.builder().name(authorName).build());
            return author.getId();
        }  else {
            return authorDao.findAuthorByText(authorName).get(0).getId();
        }
    }
}
