package com.maslov.booksmaslov.shell;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.repository.BookDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Scanner;

@ShellComponent
@Slf4j
public class CommandForShell {

    private BookDao bookDao;

    private final String ENTER_ID = "Enter ID for book or 0 is your dont now ID";
    private final String GET_ALL = "Enter command 'getall' for search your book in list";

    public CommandForShell(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    Scanner scanner = new Scanner(System.in);

    @ShellMethod(value = "Get book", key = {"g", "getbook"})
    public void getBook() {
        System.out.println(ENTER_ID);
        int id = scanner.nextInt();
        if (id > 0) {
            Book book = bookDao.getBookById(id);
            System.out.println(book);
        } else {
            System.out.println(GET_ALL);
        }
    }

    @ShellMethod(value = "Get all book", key = {"getall"})
    public void getAllBook() {
        List<Book> books = bookDao.getAllBook();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @ShellMethod(value = "Create book", key = {"createBook", "cr"})
    public void createBook() {
        System.out.println("Enter name of the book");
        String name = scanner.nextLine();
        System.out.println("Enter name of the author");
        String author = scanner.nextLine();
        System.out.println("Enter year of publish");
        String year = scanner.nextLine();
        System.out.println("Enter name of the genre");
        String genre = scanner.nextLine();
        bookDao.createBook(name, author, year, genre);
    }

    @ShellMethod(value = "Update book", key = {"updateBook", "upd"})
    public void updateBook() {
        System.out.println(ENTER_ID);
        int id = scanner.nextInt();
        if (id > 0) {
            System.out.println("Enter correct name of the book");
            String name = scanner.nextLine();
            System.out.println("Enter correct name of the author");
            String author = scanner.nextLine();
            System.out.println("Enter correct year of publish");
            String year = scanner.nextLine();
            System.out.println("Enter correct name of the genre");
            String genre = scanner.nextLine();
            log.info(bookDao.updateBook(id, name, author, year, genre).toString());
        } else {
            System.out.println(GET_ALL);
        }
    }

    @ShellMethod(value = "Delete book", key = {"d", "delbook"})
    public void delBook() {
        System.out.println(ENTER_ID);
        int id = scanner.nextInt();
        if (id > 0) {
            bookDao.deleteBook(id);
            log.info("Book deleted successfully");
        } else {
            System.out.println(GET_ALL);
        }
    }
}
