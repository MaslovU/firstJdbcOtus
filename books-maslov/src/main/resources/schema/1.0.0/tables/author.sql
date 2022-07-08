CREATE TABLE IF NOT EXISTS author(
        id bigserial,
        name varchar(255),
        book_id bigserial REFERENCES book(id),
        PRIMARY KEY (id)
)