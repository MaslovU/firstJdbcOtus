package com.maslov.booksmaslov.sql;

public class SQLConstants {
    public static final String GET_ALL_BOOKS = "select b.id as id,\n" +
            " b.name as name,\n" +
            " a.name as author,\n" +
            " b.year_of_publishing as year_of_publishing,\n" +
            " g.name as genre\n" +
            " from book b\n" +
            " join author a on cast(b.author_id as int) = a.id\n" +
            " join genre g on cast(b.genre_id as int) = g.id";

    public static final String SELECT_BOOK_BY_ID = GET_ALL_BOOKS + " where b.id=:id";

    public static final String SELECT_BOOK_BY_NAME = GET_ALL_BOOKS + " where b.name=:name";

    public static final String UPDATE_BOOK_BY_ID = "update book set id=:id, name=:name, author_id=:author_id, " +
            "year_of_publishing=:year_of_publishing, genre_id=:genre_id where id=:id";
    public static final String INSERT_INTO_BOOK = "insert into book (id, name, author_id, year_of_publishing, genre_id)" +
            "values (:id, :name, :author_id, :year_of_publishing, :genre_id)";
    public static final String DELETE_BOOK = "delete from book where id=:id";

    public static final String GET_ALL_GENRES = "select id, name from genre";
    public static final String GET_GENRE_BY_ID = "select id, name from genre where id =:id";
    public static final String GET_GENRE_BY_NAME = "select id, name from genre where name =:name";
    public static final String CREATE_GENRE = "insert into genre (id, name) values (:id, :name)";

    public static final String GET_ALL_AUTHORS = "select id, name from author";
    public static final String GET_AUTHOR_BY_ID = "select id, name from author where id =:id";
    public static final String GET_AUTHOR_BY_NAME = "select id, name from author where name =:name";
    public static final String CREATE_AUTHOR = "insert into author (id, name) values (:id, :name)";
}
