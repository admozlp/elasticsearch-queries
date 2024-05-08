package com.ademozalp.elasticsearch.exception;


import com.ademozalp.elasticsearch.dto.ErrorResult;
import com.ademozalp.elasticsearch.exception.types.ElasticSearchQueryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ElasticSearchQueryException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResult handleMethodArgumentNotValid(ElasticSearchQueryException ex) {
        return new ErrorResult(false, ex.getMessage(), 400);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResult handleMethodArgumentNotValid(Exception ex) {
        return new ErrorResult(false, ex.getMessage(), 400);
    }
}

