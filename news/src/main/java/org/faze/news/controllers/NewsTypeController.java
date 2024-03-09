package org.faze.news.controllers;

import jakarta.validation.Valid;
import org.faze.news.dto.AllNewsTypesDTO;
import org.faze.news.dto.NewsTypeDTO;
import org.faze.news.exceptions.NewsException;
import org.faze.news.exceptions.NewsTypeNotFoundException;
import org.faze.news.models.NewsType;
import org.faze.news.services.NewsTypeService;
import org.faze.news.utils.NewsTypeErrorResponse;
import org.faze.news.exceptions.NewsTypeException;
import org.faze.news.utils.NewsTypeValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.faze.news.utils.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/v3/news-types")
public class NewsTypeController {

    NewsTypeService newsTypeService;
    ModelMapper modelMapper;
    NewsTypeValidator newsTypeValidator;

    @Autowired
    public NewsTypeController(NewsTypeService newsTypesService, ModelMapper modelMapper, NewsTypeValidator newsTypeValidator) {
        this.newsTypeService = newsTypesService;
        this.modelMapper = modelMapper;
        this.newsTypeValidator = newsTypeValidator;
    }

    @GetMapping()
    public ResponseEntity<AllNewsTypesDTO> findAll() {
        AllNewsTypesDTO allNewsTypesDTO = new AllNewsTypesDTO(newsTypeService.findAll().stream().map(this::convertToNewsTypeDTO).collect(Collectors.toList()));

        return new ResponseEntity<>(allNewsTypesDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsTypeDTO> findById(@PathVariable("id") int id) {

        return new ResponseEntity<>(convertToNewsTypeDTO(newsTypeService.findById(id).orElseThrow(
                () -> new NewsTypeNotFoundException("Тип новости с id " + id + " не найден")
        )), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewsType(@RequestBody @Valid NewsTypeDTO newsTypeDTO, BindingResult bindingResult) {
        NewsType newsType = convertToNewsType(newsTypeDTO);
        newsTypeValidator.validate(newsType, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        newsTypeService.addNewsType(newsType);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateNewsType(@PathVariable("id") int id, @RequestBody @Valid NewsTypeDTO newsTypeDTO, BindingResult bindingResult) {
        NewsType newsType = convertToNewsType(newsTypeDTO);

        if(newsTypeService.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        newsTypeService.updateNewsType(id, newsType);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNewsType(@PathVariable("id") int id) {
        newsTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private NewsType convertToNewsType(NewsTypeDTO newsTypeDTO) {
        return modelMapper.map(newsTypeDTO, NewsType.class);
    }

    private NewsTypeDTO convertToNewsTypeDTO(NewsType newsType) {
        return modelMapper.map(newsType, NewsTypeDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<NewsTypeErrorResponse> handleException(NewsTypeException e) {
        NewsTypeErrorResponse response = new NewsTypeErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<NewsTypeErrorResponse> handleException(NewsTypeNotFoundException e) {
        NewsTypeErrorResponse response = new NewsTypeErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<NewsTypeErrorResponse> handleException(NewsException e) {
        NewsTypeErrorResponse response = new NewsTypeErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
