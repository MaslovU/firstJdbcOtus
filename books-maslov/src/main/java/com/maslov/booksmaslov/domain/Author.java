package com.maslov.booksmaslov.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Author {
    private final int id;
    private final String name;
}
