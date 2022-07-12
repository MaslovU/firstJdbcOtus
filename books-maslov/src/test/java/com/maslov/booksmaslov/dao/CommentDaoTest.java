package com.maslov.booksmaslov.dao;

import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.exception.NoCommentException;
import com.maslov.booksmaslov.repository.CommentRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({CommentDao.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentDaoTest {
    private static final long ID_OF_COMMENT = 1;
    private static final String FIRST_COMM = "no";
    private static final String SECOND_COMMENT = "second comment";
    private static final String UPDATE_COMMENT = "update comment";
    private static final String ERROR_MESSAGE = "No comment for this ID";

    @Autowired
    private CommentDao dao;

    @Test
    void getCommentById() {
        Comment comment = dao.getCommentById(ID_OF_COMMENT).get();

        assertThat(comment.getCommentForBook()).isEqualTo(FIRST_COMM);
    }

    @Test
    void createComment() {
        long commentId = dao.createComment(new Comment(0, SECOND_COMMENT)).getId();

        assertThat(dao.getCommentById(commentId).get().getCommentForBook()).isEqualTo(SECOND_COMMENT);
    }

    @Test
    void updateComment() {
        Comment commentFromDB = dao.getCommentById(ID_OF_COMMENT).get();
        dao.updateComment(new Comment(0, UPDATE_COMMENT), commentFromDB);

        assertThat(dao.getCommentById(ID_OF_COMMENT).get().getCommentForBook()).isEqualTo(UPDATE_COMMENT);
    }

    @Test
    void deleteComment() {
        dao.deleteComment(new Comment(ID_OF_COMMENT, FIRST_COMM));

        try {
            dao.getCommentById(ID_OF_COMMENT);
        } catch (NoCommentException e) {
            assertEquals(ERROR_MESSAGE, e.getMessage());
        }
    }
}