package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Genre;
import com.maslov.booksmaslov.repository.GenreDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maslov.booksmaslov.sql.SQLConstants.CREATE_GENRE;
import static com.maslov.booksmaslov.sql.SQLConstants.GET_ALL_GENRES;
import static com.maslov.booksmaslov.sql.SQLConstants.GET_GENRE_BY_ID;
import static com.maslov.booksmaslov.sql.SQLConstants.GET_GENRE_BY_NAME;

@RequiredArgsConstructor
@Component
@Slf4j
public class GenreDaoImpl implements GenreDao {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Genre> getAllGenres() {
        return jdbc.query(GET_ALL_GENRES, new GenreDaoImpl.GenreMapper());
    }

    @Override
    public Genre getNameById(int id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        try {
            return jdbc.queryForObject(GET_GENRE_BY_ID, paramMap, new GenreDaoImpl.GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Book with this id is not exist");
        }
        return null;
    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return jdbc.queryForObject(GET_GENRE_BY_NAME, paramMap, new GenreDaoImpl.GenreMapper());
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
        List<Genre> genres = getAllGenres();
        Collections.sort(genres);
        int id = genres.get(genres.size() - 1).getId() + 1;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("name", name);
        jdbc.update(CREATE_GENRE, paramMap);
        return id;
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
