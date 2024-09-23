package com.cms.exception;

import com.cms.dto.ObjectNotFoundDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = ObjectNotFoundException.class)
    public ResponseEntity<Object> exception(ObjectNotFoundException exception) {
        return new ResponseEntity<>(new ObjectNotFoundDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MalformedURLException.class, URISyntaxException.class})
    public ResponseEntity<ObjectNotFoundDto> invalidUrl(MalformedURLException exception) {
        return new ResponseEntity<>(new ObjectNotFoundDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
