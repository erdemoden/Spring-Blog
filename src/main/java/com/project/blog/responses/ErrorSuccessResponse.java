package com.project.blog.responses;

import lombok.Data;

import java.util.List;

@Data
public class ErrorSuccessResponse {

    String error;
    String success;

    Object object;
}
