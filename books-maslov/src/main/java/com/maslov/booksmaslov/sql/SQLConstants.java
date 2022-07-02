package com.maslov.booksmaslov.sql;

public class SQLConstants {
    private SQLConstants() {
    }

    public static final String SELECT_BOOK_BY_ID = "select * from book where id =:id";
    public static final String SELECT_BOOK_BY_NAME = "select * from book where name =: name";
    public static final String UPDATE_BOOK_BY_ID = "update book set id=:id, name=:name, author_id=:author_id, " +
            "year_of_publishing=:year_of_publishing, genre_id=:genre_id where id=:id";
    public static final String INSERT_INTO_BOOK = "insert into book (id, name, author_id, year_of_publishing, genre_id)" +
            "values (:id, :name, :author_id, :year_of_publishing, :genre_id)";
    public static final String DELETE_BOOK = "delete from book where id=:id";

    public static final String GET_ALL_GENRES = "select * from genre";
    public static final String GET_GENRE_BY_ID = "select * from genre where id =:id";
    public static final String GET_GENRE_BY_NAME = "select * from genre where name =:name";
    public static final String CREATE_GENRE = "insert into genre (id, name) values (:id, :name)";

    public static final String GET_ALL_AUTHORS = "select * from author";
    public static final String GET_AUTHOR_BY_ID = "select * from author where id =:id";
    public static final String GET_AUTHOR_BY_NAME = "select * from author where name =:name";
    public static final String CREATE_AUTHOR = "insert into author (id, name) values (:id, :name)";
}
