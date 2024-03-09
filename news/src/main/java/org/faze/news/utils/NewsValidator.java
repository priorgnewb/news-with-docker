package org.faze.news.utils;

import org.faze.news.models.News;
import org.faze.news.services.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NewsValidator implements Validator {

    NewsTypeService newsTypeService;

    @Autowired
    public NewsValidator(NewsTypeService newsTypeService) {
        this.newsTypeService = newsTypeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return News.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        News news = (News) target;

        if (news.getNewsType() == null) {
            return;
        }

        if (newsTypeService.findByType(news.getNewsType().getType()).isEmpty() ) {
            errors.rejectValue("newsType", "Указанный тип новости не существует!");
        }
    }
}
