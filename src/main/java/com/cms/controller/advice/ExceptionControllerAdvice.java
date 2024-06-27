package com.cms.controller.advice;

import com.cms.dto.ObjectNotFoundDto;
import com.cms.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = ObjectNotFoundException.class)
    public ResponseEntity<Object> exception(ObjectNotFoundException exception) {
        return new ResponseEntity<>(new ObjectNotFoundDto("Object not found"), HttpStatus.NOT_FOUND);
    }
}
