package com.maslov.booksmaslov.service.impl;

import com.maslov.booksmaslov.dao.AuthorDao;
import com.maslov.booksmaslov.dao.BookDao;
import com.maslov.booksmaslov.dao.GenreDao;
import com.maslov.booksmaslov.dao.YearDao;
import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.domain.YearOfPublish;
import com.maslov.booksmaslov.service.BookService;
import com.maslov.booksmaslov.service.ScannerHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final String ENTER_ID = "Enter ID for book or 0 is your dont now ID";
    private final String GET_ALL = "Enter command 'getall' for search your book in list";

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final YearDao yearDao;
    private final GenreDao genreDao;
    private final ScannerHelper helper;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, YearDao yearDao, GenreDao genreDao, ScannerHelper helper) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.yearDao = yearDao;
        this.genreDao = genreDao;
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
        return bookDao.createBook(getBookFromUser(0));
    }

    @Override
    public void updateBook() {
        log.debug("Start updating book. if you don't want to change the value, click Enter");
        System.out.println(ENTER_ID);
        long id = helper.getIdFromUser();
        helper.getEmptyString();
        if (id > 0) {
            Book bookFromDB = bookDao.getBookById(id).get();
            bookDao.updateBook(getBookFromUser(id), bookFromDB);
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

    private Book getBookFromUser(long idOfBook) {
        String name = getNameOfBook(idOfBook);

        List<Author> authors = getListAuthors(idOfBook);

        val year = getYear(name);

        val genre = getGenre(name);

        System.out.println("You can add comment to this book. Split your different comments by dot");
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

    private String getNameOfBook(long idOfBook) {
        System.out.println("Enter name of the book");
        String name = helper.getFromUser();
        if (name.isEmpty()) {
            return bookDao.getBookById(idOfBook).get().getName();
        } else {
            return name;
        }
    }

    private long getAuthorId(String authorName) {
        if (authorDao.findAuthorByText(authorName).isEmpty()) {
            Author author = authorDao.createAuthor(Author.builder().name(authorName).build());
            return author.getId();
        } else {
            return authorDao.findAuthorByText(authorName).get(0).getId();
        }
    }

    private List<Author> getListAuthors(long idOfBook) {
        System.out.println("Enter new names of the authors");
        List<String> authorNames = List.of(helper.getFromUser().split(","));
        List<Author> authors = new ArrayList<>();
        if (authorNames.isEmpty()) {
            try {
                return bookDao.getBookById(idOfBook).get().getAuthor();
            } catch (NullPointerException e) {
                return new ArrayList<>();
            }
        } else {
            for (String s : authorNames) {
                long authorId = getAuthorId(s);
                Author author = Author.builder().id(authorId).name(s).build();
                authors.add(author);
            }
            return authors;
        }
    }

    private YearOfPublish getYear(String nameOfBook) {
        System.out.println("Enter new years of publish");
        String year = helper.getFromUser();
        if (year.isEmpty()) {
            return yearDao.getYearByDate(bookDao.getBooksByName(nameOfBook).get(0).getYear().getDateOfPublish());
        } else {
            long yearId;
            try {
                yearId = yearDao.getYearByDate(year).getId();
            } catch (NullPointerException e) {
                yearId = yearDao.createYear(YearOfPublish.builder().dateOfPublish(year).build()).getId();
            }
            return YearOfPublish.builder().id(yearId).dateOfPublish(year).build();
        }
    }

    private Genre getGenre(String nameOfBook) {
        System.out.println("Enter new name of the genre");
        String genre = helper.getFromUser();
        if (genre.isEmpty()) {
            return genreDao.getGenreByName(bookDao.getBooksByName(nameOfBook).get(0).getGenre().getName());
        } else {
            long genreId;
            try {
                genreId = genreDao.getGenreByName(genre).getId();
            } catch (NullPointerException e) {
                genreId = genreDao.createGenre(Genre.builder().name(genre).build()).getId();
            }
            return Genre.builder().id(genreId).name(genre).build();
        }
    }
}
