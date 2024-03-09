package org.faze.news.exceptions;

public class NewsNotFoundException extends RuntimeException{
    public NewsNotFoundException(String message) {
        super(message);
    }
}
