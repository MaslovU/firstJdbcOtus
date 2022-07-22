package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Comment;

import java.util.Set;

public interface CommentDao {

    Comment getCommentById(long id);

    Set<Comment> findCommentsForBookById(Long id);

    Comment createComment(String comment);

    void updateComment(Comment comment);

    void deleteComment(Comment comment);
}
