package com.maslov.booksmaslov.service.impl;

import com.maslov.booksmaslov.dao.BookDao;
import com.maslov.booksmaslov.dao.CommentDao;
import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.service.CommentService;
import com.maslov.booksmaslov.service.ScannerHelper;
import liquibase.pro.packaged.B;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final ScannerHelper helper;
    private final BookDao bookDao;
    private final CommentDao commentDao;

    public CommentServiceImpl(ScannerHelper helper, BookDao bookDao, CommentDao commentDao) {
        this.helper = helper;
        this.bookDao = bookDao;
        this.commentDao = commentDao;
    }

    @Override
    public List<Comment> getAllCommentsForBook() {
        long idOfBook = getIdForBook();
        return bookDao.getBookById(idOfBook).get().getListOfComment();
    }

    @Override
    public List<Comment> createComment() {
        int idForBook = getIdForBook();
        helper.getEmptyString();
        System.out.println("Enter your comment");
        Comment comm = new Comment(0, helper.getFromUser());
        Comment addedComment = commentDao.createComment(comm);
        Book bookFromDB = bookDao.getBookById(idForBook).get();
        List<Comment> commentList = bookFromDB.getListOfComment();
        commentList.add(addedComment);
        Book book = Book.builder()
                .name(bookFromDB.getName())
                .genre(bookFromDB.getGenre())
                .year(bookFromDB.getYear())
                .author(bookFromDB.getAuthor())
                .listOfComment(commentList)
                .build();
        bookDao.updateBook(book, bookFromDB);
        return commentList;
    }

    @Override
    public List<Comment> updateComment() {
        int idForBook = getIdForBook();
        int idComment = getCommentId(idForBook);
        helper.getEmptyString();
        Comment commentFromDB = commentDao.getCommentById(idComment).get();
        System.out.println("Enter correct comment");
        String newComment = helper.getFromUser();
        Comment comment = Comment.builder().commentForBook(newComment).build();
        commentDao.updateComment(comment, commentFromDB);
        return bookDao.getBookById(idForBook).get().getListOfComment();
    }

    @Override
    public List<Comment> deleteComment() {
        int idForBook = getIdForBook();
        long idForComment = getCommentId(idForBook);
        Comment comment = commentDao.getCommentById(idForComment).get();
        commentDao.deleteComment(comment);
        return bookDao.getBookById(idForBook).get().getListOfComment();
    }

    private int getIdForBook() {
        System.out.println("Enter name for book");
        String nameOfBook = helper.getFromUser();
        List<Book> listOfBooks = bookDao.getBooksByName(nameOfBook);
        for (Book b : listOfBooks) {
            System.out.println(b);
        }
        System.out.println("Find id your book and enter it");
        return helper.getIdFromUser();
    }

    private int getCommentId(int idForBook) {

        for (Comment c : bookDao.getBookById(idForBook).get().getListOfComment()) {
            System.out.println(c);
        }
        System.out.println("Choose and enter id of comment");
        return helper.getIdFromUser();
    }
}
