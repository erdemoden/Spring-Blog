package com.project.blog.controllers;

import com.project.blog.requests.CommentCreateRequest;
import com.project.blog.responses.ErrorSuccessResponse;
import com.project.blog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/createcomment")
    public ErrorSuccessResponse createComment(@RequestBody @Valid CommentCreateRequest commentCreateRequest, @RequestHeader String Authorization){
        return commentService.setComment(commentCreateRequest,Authorization);
    }
}
