package com.maslov.booksmaslov.service.impl;

import com.maslov.booksmaslov.domain.Comment;
import com.maslov.booksmaslov.repository.BookDao;
import com.maslov.booksmaslov.repository.CommentDao;
import com.maslov.booksmaslov.repository.impl.CommentDaoImpl;
import com.maslov.booksmaslov.service.CommentService;
import com.maslov.booksmaslov.service.ScannerHelper;
import com.maslov.booksmaslov.service.ServiceHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
@Import(CommentDaoImpl.class)
@SpringJUnitConfig(CommentServiceImpl.class)
class CommentServiceImplTest {

    @MockBean
    private ScannerHelper helper;
    @MockBean
    private BookDao bookDao;
    @MockBean
    private CommentDao commentDao;
    @MockBean
    private ServiceHelper serviceHelper;

    @Autowired
    private CommentService service;

    @Test
    void getAllCommentsForBook() {

        when(serviceHelper.getIdForBook()).thenReturn(1);

        Set<Comment> commentsSet = service.getAllCommentsForBook();

        assert commentsSet.isEmpty();
    }
}