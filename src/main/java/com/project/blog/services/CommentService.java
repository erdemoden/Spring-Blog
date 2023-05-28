package com.project.blog.services;

import com.project.blog.entities.Comments;
import com.project.blog.entities.Posts;
import com.project.blog.entities.User;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.PostsRepository;
import com.project.blog.requests.CommentCreateRequest;
import com.project.blog.responses.ErrorSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostsRepository postsRepository;
    private final UserService userService;

    public ErrorSuccessResponse setComment(CommentCreateRequest commentCreateRequest, String authorization){
        ErrorSuccessResponse response = new ErrorSuccessResponse();
        Optional<Posts> posts = postsRepository.findById(commentCreateRequest.getPostId());
        Optional<User> user =  userService.getUserFromAuth(authorization);
        if(user == null || posts == null){
            response.setError("There is something wrong in user or post");
            return response;
        }
        Comments comments = new Comments();
        comments.setUser(user.orElse(null));
        comments.setPosts(posts.orElse(null));
        comments.setComment(commentCreateRequest.getComment());
        commentRepository.save(comments);
        response.setObject(comments);
        response.setSuccess("Comment Created Successfully");
        return response;
    }

}
