package com.maslov.booksmaslov.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Genre implements Comparable<Genre>{
    private final Integer id;
    private final String name;

    @Override
    public int compareTo(Genre o) {
        return this.getId().compareTo(o.getId());
    }
}
