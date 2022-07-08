package com.maslov.booksmaslov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
@Builder
public class BookModel {
    private String name;
    private String authors;
    private String year;
    private String genre;
}
