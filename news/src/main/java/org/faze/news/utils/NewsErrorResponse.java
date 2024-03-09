package org.faze.news.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewsErrorResponse {
    private String message;
    private long timestamp;
}
