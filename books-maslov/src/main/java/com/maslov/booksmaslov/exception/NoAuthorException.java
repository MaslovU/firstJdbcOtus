package com.maslov.booksmaslov.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoAuthorException extends RuntimeException{
    public NoAuthorException(String message) {
        super(message);
    }
}
