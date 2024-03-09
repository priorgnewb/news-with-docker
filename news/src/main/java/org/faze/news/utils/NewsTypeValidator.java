package org.faze.news.utils;

import org.faze.news.models.NewsType;
import org.faze.news.services.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class NewsTypeValidator implements Validator {

    NewsTypeService newsTypeService;

    @Autowired
    public NewsTypeValidator(NewsTypeService newsTypeService) {
        this.newsTypeService = newsTypeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewsType.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewsType newsType = (NewsType) target;
        String hexColor = newsType.getHexColor();

        Optional<NewsType> byType = newsTypeService.findByType(newsType.getType());

        if (byType.isPresent()) {
            errors.rejectValue("type", "Тип новостей с указанным названием уже существует!");
        }

//        if (byType.isPresent()) {
//            if(byType.get().getHexColor().equals(hexColor)) {
//                errors.rejectValue("hexColor", "Тип новостей с указанным названием и цветом уже существует!");
//            } else {
//                errors.rejectValue("type", "Тип новостей с указанным названием уже существует!");
//            }
//        }
        }
    }