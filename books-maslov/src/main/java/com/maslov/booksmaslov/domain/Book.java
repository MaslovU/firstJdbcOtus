package com.maslov.booksmaslov.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
@Entity
public class Book implements Comparable<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JoinColumn(name = "genre_id")
    @ManyToOne(targetEntity = Genre.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Genre genre;

    @JoinColumn(name = "year_id")
    @ManyToOne(targetEntity = Year.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Year yearOfPublishing;

    @ManyToMany(targetEntity = Author.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "book", joinColumns = @JoinColumn(name = "id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> author;

    @Override
    public int compareTo(Book b) {
        return this.getId().compareTo(b.getId());
    }
}