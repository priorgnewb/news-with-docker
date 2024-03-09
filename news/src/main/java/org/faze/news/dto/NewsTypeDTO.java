package org.faze.news.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"type","hexColor"})
public class NewsTypeDTO {

    @NotEmpty(message = "Тип новости должен быть указан!")
    @Size(min = 1, max = 30, message = "Длина типа новости должна быть от 1 до 30 символов!")
    private String type;

    @NotEmpty(message = "Цвет должен быть указан в виде #XXXXXX !")
    @Size(min = 7, max = 7, message = "Цвет должен быть указан в виде 7 символов!")
    private String hexColor;
}
