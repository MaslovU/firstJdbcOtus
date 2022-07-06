package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.exception.NoAuthorException;
import com.maslov.booksmaslov.repository.AuthorDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.maslov.booksmaslov.sql.SQLConstants.GET_ALL_AUTHORS;
import static com.maslov.booksmaslov.sql.SQLConstants.GET_AUTHOR_BY_NAME;
import static java.util.Objects.isNull;

@Repository
@Slf4j
//@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
//    @Autowired
//    @Qualifier(value = "entityManager")
    private final EntityManager em;

    public AuthorDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> getAllAuthors() {
//        return jdbc.query(GET_ALL_AUTHORS, new AuthorDaoImpl.AuthorMapper());
        var query = em.createQuery(GET_ALL_AUTHORS, Author.class);
        return query.getResultList();
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = em.createQuery(GET_AUTHOR_BY_NAME, Author.class);
        query.setParameter("name", name);
        Author author = checkResult(query, name);
        return author;
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("id", id);
//        try {
//            return jdbc.queryForObject(GET_AUTHOR_BY_ID, paramMap, new AuthorDaoImpl.AuthorMapper());
//        } catch (EmptyResultDataAccessException e) {
//            log.error("Book with this id is not exist");
//        }
//        return null;
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    @Transactional
    public Author createAuthor(Author author) {
        log.info("Created new Author");
//        em.detach(author);
//        List<Author> listAuthors = getAllAuthors();
//        Collections.sort(listAuthors);
//        int id = listAuthors.get(getAllAuthors().size() - 1).getId() + 1;
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("id", id);
//        paramMap.put("name", name);
//        jdbc.update(CREATE_AUTHOR, paramMap);
        if (isNull(author.getId())) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }


    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }

    private Author checkResult(TypedQuery<Author> query, String name) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            log.warn("Has not author with name: {}", name);
            throw new NoAuthorException(String.format("Has not author with name %s", name));
        }
    }
}
