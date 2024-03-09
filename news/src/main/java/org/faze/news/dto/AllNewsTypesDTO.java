package org.faze.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllNewsTypesDTO {
    private List<NewsTypeDTO> allTypes;
}