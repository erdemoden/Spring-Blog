package com.project.blog.Exceptions;

import lombok.Data;

@Data
public class CustomErrorDTO {
    private String error;
    private String route;
}
