package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Author;
import com.maslov.booksmaslov.repository.AuthorDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcOperations jdbc;

    @Override
    public List<Author> getAllNames() {
        return jdbc.query("select * from author", new AuthorDaoImpl.AuthorMapper());
    }

    @Override
    public Author getByName(String name) {
        return jdbc.queryForObject("select * from author where name =?", new AuthorDaoImpl.AuthorMapper(), name);
    }

    @Override
    public String getNameById(int id) {
        return jdbc.queryForObject("select * from author where id =?", new AuthorDaoImpl.AuthorMapper(), id).getName();
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
        jdbc.update("insert into author (id, name) values (?, ?)", id, name);
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
