package com.maslov.booksmaslov.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@RequiredArgsConstructor
@Data
public class Book implements Comparable<Book> {
    private final Integer id;
    private final String name;
    @Fetch(FetchMode.SUBSELECT)
    private final String author;
    private final String yearOfPublishing;
    private final String genre;

    @Override
    public int compareTo(Book b) {
        return this.getId().compareTo(b.getId());
    }
}