package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.repository.AuthorDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maslov.booksmaslov.sql.SQLConstants.CREATE_AUTHOR;
import static com.maslov.booksmaslov.sql.SQLConstants.GET_ALL_AUTHORS;
import static com.maslov.booksmaslov.sql.SQLConstants.GET_AUTHOR_BY_ID;
import static com.maslov.booksmaslov.sql.SQLConstants.GET_AUTHOR_BY_NAME;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Author> getAllNames() {
        return jdbc.query(GET_ALL_AUTHORS, new AuthorDaoImpl.AuthorMapper());
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return jdbc.queryForObject(GET_AUTHOR_BY_NAME, paramMap, new AuthorDaoImpl.AuthorMapper());
    }

    @Override
    public Author getNameById(int id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        try {
            return jdbc.queryForObject(GET_AUTHOR_BY_ID, paramMap, new AuthorDaoImpl.AuthorMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Book with this id is not exist");
        }
        return null;
    }

    @Override
    public String getAuthorId(String name) {
        try {
            return String.valueOf(getByName(name).getId());
        } catch (EmptyResultDataAccessException e) {
            return String.valueOf(createAuthor(name));
        }
    }

    @Override
    public int createAuthor(String name) {
        log.info("Created new Author");
        int id = getAllNames().size() + 1;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("name", name);
        jdbc.update(CREATE_AUTHOR, paramMap);
        return id;
    }


    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
