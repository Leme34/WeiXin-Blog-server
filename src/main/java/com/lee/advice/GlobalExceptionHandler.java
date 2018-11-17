package com.lee.advice;

import com.lee.enums.BlogExceptionEnum;
import com.lee.exception.BlogException;
import com.lee.vo.BlogResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public ResponseEntity handlerBlogException(BlogException e) {
        BlogExceptionEnum em = e.getExceptionEnum();
        return ResponseEntity.status(em.getStatusCode())
                .body(new BlogResponseResult(em));
    }

}
