package com.maslov.booksmaslov.dao;

import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.repository.CommentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class CommentDao {

    private final CommentRepo repo;

    public CommentDao(CommentRepo repo) {
        this.repo = repo;
    }

    public Optional<Comment> getCommentById(long id) {
        return repo.findById(id);
    }

    public Comment createComment(Comment comment) {
        return repo.save(comment);
    }

    public void updateComment(Comment comment, Comment commentFromDB) {
        BeanUtils.copyProperties(comment, commentFromDB, "id");
        repo.save(commentFromDB);
    }

    public void deleteComment(Comment comment) {
        repo.deleteById(comment.getId());
    }
}
