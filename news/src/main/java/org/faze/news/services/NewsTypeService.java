package org.faze.news.services;

import org.faze.news.exceptions.NewsNotFoundException;
import org.faze.news.exceptions.NewsTypeException;
import org.faze.news.exceptions.NewsTypeNotFoundException;
import org.faze.news.models.NewsType;
import org.faze.news.repositories.NewsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class NewsTypeService {

    NewsTypeRepository newsTypeRepository;

    @Autowired
    public NewsTypeService(NewsTypeRepository newsTypeRepository) {
        this.newsTypeRepository = newsTypeRepository;
    }

    public Optional<NewsType> findByType(String type) {
        return newsTypeRepository.findByType(type);
    }

    public Optional<NewsType> findById(int id) {
        return newsTypeRepository.findById(id);
    }

    @Transactional
    public void addNewsType(NewsType newsType) {
        newsTypeRepository.save(newsType);
    }

    @Transactional
    public void updateNewsType(int id, NewsType newsType) {
        newsType.setId(id);
        newsType.setHexColor(newsType.getHexColor());
        newsTypeRepository.save(newsType);
    }

    @Transactional
    public void deleteById(int id) {
        newsTypeRepository.findById(id).orElseThrow(
                () -> new NewsTypeException("Тип новостей с id " + id + " не найден!"));
        newsTypeRepository.deleteById(id);
    }

    public List<NewsType> findAll(){
       return newsTypeRepository.findAll();
    }
}
