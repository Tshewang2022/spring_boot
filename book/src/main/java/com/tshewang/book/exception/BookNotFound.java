package com.tshewang.book.exception;

public class BookNotFound extends RuntimeException{
    public BookNotFound(String message) {
        super(message);
    }

    public BookNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotFound(Throwable cause) {
        super(cause);
    }
}
