package com.maslov.booksmaslov.service.impl;

import com.maslov.booksmaslov.domain.Book;
import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.exception.MaslovBookException;
import com.maslov.booksmaslov.repository.BookDao;
import com.maslov.booksmaslov.repository.CommentDao;
import com.maslov.booksmaslov.service.CommentService;
import com.maslov.booksmaslov.service.ScannerHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Set<Comment> getAllCommentsForBook() {
        long idOfBook = getIdForBook();
        return commentDao.findCommentsForBookById(idOfBook);
    }

    @Transactional
    @Override
    public Set<Comment> createComment() {
        int idForBook = getIdForBook();
        System.out.println("Enter your comment");
        String comm = helper.getFromUser();
        Comment addedComment = commentDao.createComment(comm);
        Set<Comment> commentList = bookDao.getBookById(idForBook)
                .orElseThrow(() -> new MaslovBookException("No comments")).getListOfComment();
        commentList.add(addedComment);
        return commentList;
    }

    @Transactional
    @Override
    public Set<Comment> updateComment() {
        int idForBook = getIdForBook();
        int idComment = getCommentId(idForBook);
        System.out.println("Enter correct comment");
        String newComment = helper.getFromUser();
        commentDao.updateComment(new Comment(idComment, newComment));
        return bookDao.getBookById(idForBook).get().getListOfComment();
    }

    @Override
    public Set<Comment> deleteComment() {
        int idForBook = getIdForBook();
        int idForComment = getCommentId(idForBook);
        Comment comment = commentDao.getCommentById(idForComment);
        commentDao.deleteComment(comment);
        Set<Comment> commentList = bookDao.getBookById(idForBook).get().getListOfComment();
        return commentList;
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
        System.out.println("Choose and enter id of comment");
        for (Comment c : bookDao.getBookById(idForBook).get().getListOfComment()) {
            System.out.println(c);
        }
        return helper.getIdFromUser();
    }
}
