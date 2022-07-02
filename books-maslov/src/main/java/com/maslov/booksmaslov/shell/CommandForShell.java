package com.maslov.booksmaslov.shell;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.repository.BookDao;
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

    @ShellMethod(value = "Get book", key = {"g", "getbook"})
    public void getBook() {
        System.out.println("Enter ID for book");
        int id = scanner.nextInt();
        Book book = bookDao.getBookById(id);
        System.out.println(book);
    }
}
