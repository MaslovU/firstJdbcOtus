package com.maslov.booksmaslov.shell;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.repository.BookDao;
import liquibase.pro.packaged.S;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Scanner;

@ShellComponent
public class CommandForShell {

    private BookDao bookDao;

    public CommandForShell(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    Scanner scanner = new Scanner(System.in);

    @ShellMethod(value = "Create book", key = {"createBook"})
    public void createBook() {
        System.out.println("Hello");
        int id = 0;
        String name = null;
        String author = null;
        String year = null;
        String genre = null;
        bookDao.createBook(id, name, author, year, genre);
    }

    @ShellMethod(value = "Get book", key = {"g", "getbook"})
    public void getBook() {
        System.out.println("Enter ID for book");
        int id = scanner.nextInt();
        Book book = bookDao.getBookById(id);
        System.out.println(book);
    }

//    @ShellMethod
//    public Long getId() {
//
//    }
}
