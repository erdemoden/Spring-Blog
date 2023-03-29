package com.project.blog.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class DuplicateColumnValue {

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<CustomErrorDTO> customResponse(){
        CustomErrorDTO customErrorDTO = new CustomErrorDTO();
        customErrorDTO.setError("There Is At Least One Fıeld Which Should Be Unique Please Check The Given Title Could Be Exist");
        return new ResponseEntity<>(customErrorDTO, HttpStatus.BAD_REQUEST);
    }

}
