package org.faze.news.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"title", "summary", "fullNews", "newsType"})
public class NewsDTO {

    @NotEmpty
    @Size(min = 1, max = 100, message = "Длина заголовка новости должна быть от 1 до 100 символов!")
    private String title;

    @NotEmpty
    @Size(min = 1, max = 300, message = "Длина сводки новости должна быть от 1 до 300 символов!")
    private String summary;

    @NotEmpty
    @Size(min = 1, max = 10_000, message = "Длина новости должна быть от 1 до 10000 символов!")
    private String fullNews;

    @NotNull
    private NewsTypeDTO newsType;
}
