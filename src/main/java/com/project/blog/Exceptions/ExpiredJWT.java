package com.project.blog.Exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpiredJWT {

    @ExceptionHandler({ExpiredJwtException.class})
    ResponseEntity<CustomErrorDTO> customError(){
        CustomErrorDTO customErrorDTO = new CustomErrorDTO();
        customErrorDTO.setRoute("/");
        return new ResponseEntity<>(customErrorDTO, HttpStatus.BAD_REQUEST);
    }
}
