package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.repository.BookDao;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BookDaoImpl implements BookDao {
    private final JdbcOperations jdbc;

    public BookDaoImpl(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> getAllBook() {
        return jdbc.query("select * from persons", new PersonMapper());
    }

    @Override
    public Book getBookById(int id) {
        return jdbc.queryForObject("select * from public.book where id =?", new PersonMapper(), id);
    }

    @Override
    public List<Book> getBooksByName(String name) {
        return jdbc.query("select * from book where name =?", new PersonMapper(), name);
    }

    @Override
    public void createBook(String name, String author, String year, String genre) {
        int id = getAllBook().size() + 1;
        jdbc.update("insert into book (id, name, author, year, genre) " +
                "values (?, ?, ?, ?, ?)", id, name, author, year, genre);
    }

    @Override
    public Book updateBook(int id, String name, String author, String year, String genre) {
        jdbc.update("update book set id=?, name=?, author=?, year=?, genre=? " +
                "where id=?", id, name, author, year, genre);
        return getBookById(id);
    }

    @Override
    public void deleteBook(Long id) {
        jdbc.update("delete from book where id=?", id);
    }

    private static class PersonMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String authorId = resultSet.getString("author_id");
            String year = resultSet.getString("year_of_publishing");
            String genreId = resultSet.getString("genre_id");
            return new Book(id, name, authorId, year, genreId);
        }
    }
}
