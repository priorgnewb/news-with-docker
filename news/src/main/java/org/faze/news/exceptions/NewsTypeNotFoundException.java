package org.faze.news.exceptions;

public class NewsTypeNotFoundException extends RuntimeException{
    public NewsTypeNotFoundException(String message) {
        super(message);
    }
}
