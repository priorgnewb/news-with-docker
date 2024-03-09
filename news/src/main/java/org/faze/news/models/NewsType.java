package org.faze.news.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "news_types", uniqueConstraints = @UniqueConstraint(columnNames = {"type"}))
public class NewsType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "type", unique = true)
    @NotEmpty(message = "Тип новости должен быть указан!")
    @Size(min = 1, max = 30, message = "Длина типа новости должна быть от 1 до 30 символов!")
    String type;

    @Column(name = "hex_color")
    @NotEmpty(message = "Цвет должен быть указан в виде #XXXXXX !")
    @Size(min = 7, max = 7, message = "Цвет должен быть указан в виде 7 символов!")
    String hexColor;
}