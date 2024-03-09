package org.faze.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllNewsDTO {
    private List<NewsShortDTO> allNews;
}