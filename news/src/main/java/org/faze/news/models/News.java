package org.faze.news.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "all_news")
public class News {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title")
    @NotEmpty
    @Size(min = 1, max = 100, message = "Длина заголовка новости должна быть от 1 до 100 символов!")
    private String title;

    @Column(name="summary")
    @NotEmpty
    @Size(min = 1, max = 300, message = "Длина сводки новости должна быть от 1 до 300 символов!")
    private String summary;

    @Column(name="full_news")
    @NotEmpty
    @Size(min = 1, max = 10_000, message = "Длина новости должна быть от 1 до 10000 символов!")
    private String fullNews;

    @ManyToOne
    @JoinColumn(name = "news_type", referencedColumnName = "id")
    @NotNull
    private NewsType newsType;
}
