package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.exception.NoAuthorException;
import com.maslov.booksmaslov.repository.AuthorDao;
import com.maslov.booksmaslov.repository.BookDao;
import com.maslov.booksmaslov.repository.GenreDao;
import com.maslov.booksmaslov.repository.YearDao;
import liquibase.pro.packaged.Q;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
import static java.util.Objects.isNull;

@Repository
@Slf4j
public class BookDaoImpl implements BookDao {
    @PersistenceContext
    private final EntityManager em;
    @Autowired
    private final AuthorDao authorDao;
    @Autowired
    private final YearDao yearDao;
    @Autowired
    private final GenreDao genreDao;

    public BookDaoImpl(EntityManager em, AuthorDao authorDao, YearDao yearDao, GenreDao genreDao) {
        this.em = em;
        this.authorDao = authorDao;
        this.yearDao = yearDao;
        this.genreDao = genreDao;

    }

    @Override
    public List<Book> getAllBook() {
        var allBook = em.createQuery(GET_ALL_BOOKS, Book.class);
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
    @Transactional
    public Book createBook(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    @Transactional
    public Optional<Book> updateBook(long id, String name, String author, long authorId) {
        List<String> authors = Stream.of(author.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        Query query = em.createQuery(UPDATE_BOOK_BY_ID);
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.executeUpdate();
        Query query1 = em.createQuery(UPDATE_AUTHORS_BY_ID);
        query1.setParameter("author_name", authors);
        query1.setParameter("author_id", authorId);
        query1.executeUpdate();
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
            throw new NoAuthorException(String.format("Has not author with name %s", name));
        }
    }
}
