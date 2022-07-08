package com.maslov.booksmaslov.sql;

public class SQLConstants {
    private SQLConstants() {
    }

    public static final String GET_ALL_BOOKS = "select b from Book b";
    public static final String SELECT_BOOK_BY_NAME = "select b from Book b where b.name =:name";
    public static final String UPDATE_BOOK_BY_ID = "update Book b set b.id=:id, b.name=:name, b.author_id=:author_id, " +
            "b.year_of_publishing=:year_of_publishing, b.genre_id=:genre_id where b.id=:id";
    public static final String DELETE_BOOK = "delete from Book b where b.id=:id";

    public static final String GET_ALL_GENRES = "select g from Genre g";
    public static final String GET_GENRE_BY_NAME = "select g from Genre g where g.name =:name";

    public static final String GET_ALL_AUTHORS = "select a from Author a";
    public static final String GET_AUTHOR_BY_NAME = "select a from Author a where a.name =:name";

    public static final String GET_ALL_YEARS = "select y from Year y";
    public static final String GET_YEAR_BY_DATE = "select y from Year y where y.year =:year";
}
