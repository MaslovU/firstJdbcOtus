package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.exception.MaslovBookException;
import com.maslov.booksmaslov.repository.CommentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static java.util.Objects.isNull;

@Slf4j
@Repository
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    private final EntityManager em;

    public CommentDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment getCommentById(long id) {
        if (isNull(em.find(Comment.class, id))) {
            throw new MaslovBookException("No comment for this ID");
        }
        return em.find(Comment.class, id);
    }

    @Override
    public Comment createComment(String comment) {
        Comment comm = new Comment(0, comment);
        log.info("Created new Comment");
        em.persist(comm);
        return comm;
    }

    @Override
    public void updateComment(Comment comment) {
        em.merge(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }
}
