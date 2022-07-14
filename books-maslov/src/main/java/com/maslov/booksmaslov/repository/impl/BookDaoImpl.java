package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.exception.MaslovBookException;
import com.maslov.booksmaslov.repository.BookDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.maslov.booksmaslov.sql.SQLConstants.DELETE_BOOK;
import static com.maslov.booksmaslov.sql.SQLConstants.GET_ALL_BOOKS;
import static com.maslov.booksmaslov.sql.SQLConstants.SELECT_BOOK_BY_NAME;
import static com.maslov.booksmaslov.sql.SQLConstants.UPDATE_AUTHORS_BY_ID;
import static com.maslov.booksmaslov.sql.SQLConstants.UPDATE_BOOK_BY_ID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookDaoImpl implements BookDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> getAllBook() {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-entity-graph");
        var allBook = em.createQuery(GET_ALL_BOOKS, Book.class);

        allBook.setHint("javax.persistence.fetchgraph", entityGraph);

        return allBook.getResultList();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getBooksByName(String name) {
        TypedQuery<Book> query = em.createQuery(SELECT_BOOK_BY_NAME, Book.class);
        query.setParameter("name", name);
        return checkResult(query, name);
    }

    @Override
    public Book createBook(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public Optional<Book> updateBook(long id, String name, String author, long authorId) {
        Book bookFromDB = em.find(Book.class, id);
        List<Author> authors = Stream.of(author.split(","))
                .map((s) ->new Author(authorId, s))
                .collect(Collectors.toList());
        Book book = new Book(id, name, bookFromDB.getGenre(), bookFromDB.getYear(), authors, bookFromDB.getListOfComment());
        em.merge(book);
        em.merge(authors);
        return getBookById(id);
    }

    @Override
    public void deleteBook(Long id) {
        Query query = em.createQuery(DELETE_BOOK);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private List<Book> checkResult(TypedQuery<Book> query, String name) {
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            log.warn("Has not author with name: {}", name);
            throw new MaslovBookException(String.format("Has not author with name %s", name));
        }
    }
}
