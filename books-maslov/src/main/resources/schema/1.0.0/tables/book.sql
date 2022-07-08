CREATE TABLE IF NOT EXISTS book(
    id bigserial,
    name varchar(255),
    year_id bigserial REFERENCES years(id),
    genre_id bigserial REFERENCES genre(id),
    PRIMARY KEY (id)
);

