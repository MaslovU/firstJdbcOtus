package com.maslov.booksmaslov.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book implements Comparable<Book> {
    private final Integer id;
    private final String name;
    private final String author;
    private final String yearOfPublishing;
    private final String genre;

    @Override
    public int compareTo(Book b) {
        return this.getId().compareTo(b.getId());
    }
}