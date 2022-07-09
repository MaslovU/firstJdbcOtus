CREATE TABLE comments (
     id bigserial,
     comment_for_book varchar(255),
     PRIMARY KEY (id)
);

ALTER TABLE books
ADD COLUMN comment_id bigint REFERENCES comments(id) on delete cascade;