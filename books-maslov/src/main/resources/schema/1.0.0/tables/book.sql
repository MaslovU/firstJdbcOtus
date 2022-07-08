CREATE TABLE genres(
    id bigserial,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE year_of_publish (
   id bigserial,
   year varchar(255),
   PRIMARY KEY (id)
);

CREATE TABLE books(
    id bigserial,
    name varchar(255),
    year_id bigint REFERENCES year_of_publish(id) on delete cascade ,
    genre_id bigint REFERENCES genres(id) on delete cascade ,
    PRIMARY KEY (id)
);

CREATE TABLE authors(
    id bigserial,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE book_authors(
     book_id bigint REFERENCES books(id) ON UPDATE CASCADE ON DELETE CASCADE,
     author_id  bigint REFERENCES authors(id) ON UPDATE CASCADE,
     amount numeric NOT NULL DEFAULT 1,
     CONSTRAINT book_authors_pkey PRIMARY KEY (book_id, author_id)
);
