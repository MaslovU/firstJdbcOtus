CREATE TABLE book_authors(
    book_id bigserial REFERENCES book(id) ON DELETE CASCADE,
    author_id  bigserial REFERENCES author(id),
    PRIMARY KEY (book_id, author_id)
);