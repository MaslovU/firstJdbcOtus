insert into genres(name)
values ('study'), ('chill');

insert into year_of_publish(year)
values ('2020'), ('2018');

insert into books(name, year_id, genre_id)
values ('java', 1, 1), ('go', 2, 2);

insert into comments(comment_for_book, book_id)
values ('first comment', 1);

insert into authors(author_name)
values ('lafore'), ('landay');

insert into book_authors(book_id, author_id)
values (1, 1), (2,2);

