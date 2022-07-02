package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.repository.GenreDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class GenreDaoImpl implements GenreDao {
    private final JdbcOperations jdbc;

    @Override
    public List<Genre> getAllNames() {
        return jdbc.query("select * from genre", new GenreDaoImpl.GenreMapper());
    }

    @Override
    public String getNameById(int id) {
        return jdbc.queryForObject("select * from genre where id =?", new GenreDaoImpl.GenreMapper(), id).getName();
    }

    public Genre getByName(String name) {
        return jdbc.queryForObject("select * from genre where name =?", new GenreDaoImpl.GenreMapper(), name);
    }

    @Override
    public String getAuthorId(String name) {
        try {
            return String.valueOf(getByName(name).getId());
        } catch (EmptyResultDataAccessException e) {
            return String.valueOf(createGenre(name));
        }
    }

    @Override
    public int createGenre(String name) {
        log.info("Created new Genre");
        int id = getAllNames().size() + 1;
        jdbc.update("insert into genre (id, name) values (?, ?)", id, name);
        return id;
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
