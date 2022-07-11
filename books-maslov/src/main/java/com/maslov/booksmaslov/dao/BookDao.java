package com.maslov.booksmaslov.dao;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.exception.NoAuthorException;
import com.maslov.booksmaslov.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookDao {

    private final BookRepo repo;

    public List<Book> getAllBook() {
        return repo.findAll();
    }

    public Optional<Book> getBookById(long id) {
        return repo.findById(id);
    }

    public List<Book> getBooksByName(String name) {
        try {
            return repo.getBooksByName(name);
        } catch (NoResultException e) {
            log.warn("Has not author with name: {}", name);
            throw new NoAuthorException(String.format("Has not author with name %s", name));
        }

    }

    @Transactional
    public Book createBook(Book book) {
        return repo.save(book);
    }

    @Transactional
    public Book updateBook(Book book, Book bookFromDB) {
        BeanUtils.copyProperties(book, bookFromDB, "id");
        return repo.save(bookFromDB);
    }

    public void deleteBook(Long id) {
        repo.deleteById(id);
    }
}
