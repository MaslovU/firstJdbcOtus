CREATE TABLE IF NOT EXISTS book (
    id int NOT NULL UNIQUE,
    name text,
    author_id text,
    year_of_publishing text,
    genre_id text,
    PRIMARY KEY (id)
--     CONSTRAINT uc_genge_id FOREIGN KEY (genre_id),
--     CONSTRAINT uc_author_id FOREIGN KEY (author_id)
);