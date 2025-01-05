package com.codingshuttle.securityapp.utils;

import com.codingshuttle.securityapp.entities.User;
import com.codingshuttle.securityapp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long authorId = postService.getPostById(postId).getAuthor().getId();
        return Objects.equals(user.getId(), authorId);
    }
}
