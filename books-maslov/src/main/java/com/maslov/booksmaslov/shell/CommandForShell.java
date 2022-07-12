package com.maslov.booksmaslov.shell;

import com.maslov.booksmaslov.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class CommandForShell {
    private BookService service;

    @ShellMethod(value = "Get book", key = {"g", "getbook"})
    public void getBook() {
        service.getBook();
    }

    @ShellMethod(value = "Get all book", key = {"getall"})
    public void getAllBook() {
        service.getAllBook();
    }

    @ShellMethod(value = "Create book", key = {"createBook", "cr"})
    public void createBook() {
        service.createBook();
    }

    @ShellMethod(value = "Update book", key = {"updateBook", "upd"})
    public void updateBook() {
        service.updateBook();
    }

    @ShellMethod(value = "Delete book", key = {"d", "delbook"})
    public void delBook() {
        service.delBook();
    }
}
