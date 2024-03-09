package org.faze.news.services;

import org.faze.news.exceptions.NewsNotFoundException;
import org.faze.news.exceptions.NewsTypeException;
import org.faze.news.models.News;
import org.faze.news.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class NewsService {

    NewsRepository newsRepository;
    NewsTypeService newsTypeService;

    @Autowired
    public NewsService(NewsRepository newsRepository, NewsTypeService newsTypeService) {
        this.newsRepository = newsRepository;
        this.newsTypeService = newsTypeService;
    }

    public List<News> findAll(){
       return newsRepository.findAllByOrderByIdDesc();
    }

    public List<News> findAllNewsByType(String type){
        if(newsTypeService.findByType(type).isEmpty()) {
            throw new NewsNotFoundException("Тип новости " + type + " не найден!");
        }
        return newsRepository.findAllNewsByNewsType(type);
    }

    @Transactional
    public void addNews(News news){
        enrichNews(news);
        newsRepository.save(news);
    }

    public void enrichNews(News news) {
        news.setNewsType(newsTypeService.findByType(news.getNewsType().getType()).get());
    }

    @Transactional
    public void updateNews(int id, News news){
        enrichNews(news);
        news.setId(id);
        newsRepository.save(news);
    }

    public News findById(int id) {
        return newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("Новость с id " + id + " не найдена!"));
    }

    @Transactional
    public void deleteById(int id) {
        newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("Новость с id " + id + " не найдена!"));
        newsRepository.deleteById(id);
    }
}
