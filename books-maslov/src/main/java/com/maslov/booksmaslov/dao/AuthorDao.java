package com.maslov.booksmaslov.dao;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.exception.NoAuthorException;
import com.maslov.booksmaslov.repository.AuthorRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class AuthorDao {

    private final AuthorRepo repo;

    @Autowired
    public AuthorDao(AuthorRepo repo) {
        this.repo = repo;
    }

    public List<Author> getAllAuthors() {
        return repo.findAll();
    }

    public List<Author> getByName(String name) {
        try {
            return repo.getByName(name);
        } catch (NoResultException e) {
            log.warn("Has not author with name: {}", name);
            throw new NoAuthorException(String.format("Has not author with name %s", name));
        }

    }

    public Optional<Author> getAuthorById(long id) {
        return repo.findById(id);
    }

    @Transactional
    public Author createAuthor(Author author) {
        log.info("Created new Author");
        return repo.save(author);
    }
}
