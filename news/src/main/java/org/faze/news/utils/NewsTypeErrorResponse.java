package org.faze.news.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewsTypeErrorResponse {
    private String message;
    private long timestamp;
}
