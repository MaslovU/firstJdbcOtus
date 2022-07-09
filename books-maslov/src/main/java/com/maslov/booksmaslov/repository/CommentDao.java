package com.maslov.booksmaslov.repository;

import com.maslov.booksmaslov.domain.Comment;

import java.util.List;

public interface CommentDao {

    Comment getCommentById(int id);
    Comment createComment(String comment);

    void updateComment(Comment comment);

    void deleteComment(Comment comment);
}
