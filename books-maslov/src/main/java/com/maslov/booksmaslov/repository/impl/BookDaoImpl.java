package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.repository.AuthorDao;
import com.maslov.booksmaslov.repository.BookDao;
import com.maslov.booksmaslov.repository.GenreDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maslov.booksmaslov.sql.SQLConstants.DELETE_BOOK;
import static com.maslov.booksmaslov.sql.SQLConstants.INSERT_INTO_BOOK;
import static com.maslov.booksmaslov.sql.SQLConstants.SELECT_BOOK_BY_ID;
import static com.maslov.booksmaslov.sql.SQLConstants.SELECT_BOOK_BY_NAME;
import static com.maslov.booksmaslov.sql.SQLConstants.UPDATE_BOOK_BY_ID;

@Component
@Slf4j
@AllArgsConstructor
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcTemplate namedParamJdbcTempl;

    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public List<Book> getAllBook() {
        return namedParamJdbcTempl.query("select * from book", new BookMapper(authorDao, genreDao));
    }

    @Override
    public Book getBookById(int id) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            return namedParamJdbcTempl.queryForObject(SELECT_BOOK_BY_ID, paramMap, new BookMapper(authorDao, genreDao));
        } catch (EmptyResultDataAccessException e) {
            log.error("Book with this id is not exist");
        }
        return null;
    }

    @Override
    public List<Book> getBooksByName(String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return namedParamJdbcTempl.query(SELECT_BOOK_BY_NAME, paramMap, new BookMapper(authorDao, genreDao));
    }

    @Override
    public void createBook(String name, String author, String year, String genre) {
        int id = getAllBook().size() + 1;
        getAuthorId(id, name, author, year, genre, INSERT_INTO_BOOK);
    }

    @Override
    public Book updateBook(int id, String name, String author, String year, String genre) {
        getAuthorId(id, name, author, year, genre, UPDATE_BOOK_BY_ID);
        return getBookById(id);
    }

    private void getAuthorId(int id, String name, String author, String yearOfPublishing, String genre, String sql) {
        String authorId = authorDao.getAuthorId(author);
        String genreId = genreDao.getAuthorId(genre);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("name", name);
        paramMap.put("author_id", authorId);
        paramMap.put("year_of_publishing", yearOfPublishing);
        paramMap.put("genre_id", genreId);
        namedParamJdbcTempl.update(sql, paramMap);
    }

    @Override
    public void deleteBook(int id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        namedParamJdbcTempl.update(DELETE_BOOK, paramMap);
    }

    private static class BookMapper implements RowMapper<Book> {

        private final AuthorDao authorDao;
        private final GenreDao genreDao;

        private BookMapper(AuthorDao authorDao, GenreDao genreDao) {
            this.authorDao = authorDao;
            this.genreDao = genreDao;
        }

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int authorId = Integer.parseInt(resultSet.getString("author_id"));
            String author = authorDao.getNameById(authorId).getName();
            String year = resultSet.getString("year_of_publishing");
            int genreId = Integer.parseInt(resultSet.getString("genre_id"));
            String genre = genreDao.getNameById(genreId).getName();
            return new Book(id, name, author, year, genre);
        }
    }
}
