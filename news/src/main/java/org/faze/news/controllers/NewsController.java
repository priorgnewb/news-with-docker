package org.faze.news.controllers;

import jakarta.validation.Valid;
import org.faze.news.dto.AllNewsDTO;
import org.faze.news.dto.NewsDTO;
import org.faze.news.dto.NewsShortDTO;
import org.faze.news.exceptions.NewsNotFoundException;
import org.faze.news.models.News;
import org.faze.news.services.NewsService;
import org.faze.news.utils.NewsErrorResponse;
import org.faze.news.exceptions.NewsException;
import org.faze.news.utils.NewsTypeErrorResponse;
import org.faze.news.utils.NewsValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.faze.news.utils.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/v3/news")
public class NewsController {

    NewsService newsService;
    ModelMapper modelMapper;
    NewsValidator newsValidator;

    @Autowired
    public NewsController(NewsService newsService, ModelMapper modelMapper, NewsValidator newsValidator) {
        this.newsService = newsService;
        this.modelMapper = modelMapper;
        this.newsValidator = newsValidator;
    }

    @GetMapping()
    public ResponseEntity<AllNewsDTO> findAll() {
        AllNewsDTO allNewsDTO = new AllNewsDTO(newsService.findAll().stream().map(this::convertToNewsShortDTO).collect(Collectors.toList()));

        return new ResponseEntity<>(allNewsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> findById(@PathVariable("id") int id) {
        return new ResponseEntity<>(convertToNewsFullDTO(newsService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/rubrics/{type}")
    public ResponseEntity<AllNewsDTO> findAllNewsByType(@PathVariable("type") String type) {
        List<NewsShortDTO> collect = newsService.findAllNewsByType(type).stream().map(this::convertToNewsShortDTO).toList();

        return new ResponseEntity<>(new AllNewsDTO((collect)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNews(@RequestBody @Valid NewsDTO newsDTO, BindingResult bindingResult) {
        News news = convertToNews(newsDTO);

        newsValidator.validate(news, bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        newsService.addNews(news);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateNews(@PathVariable("id") int id, @RequestBody @Valid NewsDTO newsDTO, BindingResult bindingResult) {
        News news = convertToNews(newsDTO);

        newsValidator.validate(news, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        newsService.updateNews(id, news);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id") int id) {
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private News convertToNews(NewsDTO newsDTO) {
        return modelMapper.map(newsDTO, News.class);
    }

    private NewsDTO convertToNewsFullDTO(News news) {
        return modelMapper.map(news, NewsDTO.class);
    }

    private NewsShortDTO convertToNewsShortDTO(News news) {
        return modelMapper.map(news, NewsShortDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<NewsErrorResponse> handleException(NewsException e) {
        NewsErrorResponse response = new NewsErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<NewsErrorResponse> handleException(NewsNotFoundException e) {
        NewsErrorResponse response = new NewsErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
