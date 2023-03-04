package com.project.blog.Exceptions;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

@ControllerAdvice
public class FileNotFound {

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<FileSystemResource> customResponse(FileNotFoundException exception){
        FileSystemResource resource;
        try {
          resource = new FileSystemResource(Paths.get(System.getProperty("user.dir") + "/logo/userlogo.jpg"));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new RuntimeException();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
        return new ResponseEntity<>(resource,headers,HttpStatus.ACCEPTED);
    }

    }
