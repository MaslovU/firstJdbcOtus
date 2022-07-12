package com.maslov.booksmaslov.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Author implements Comparable<Author> {
    private final Integer id;
    private final String name;

    @Override
    public int compareTo(Author o) {
        return this.getId().compareTo(o.getId());
    }
}
