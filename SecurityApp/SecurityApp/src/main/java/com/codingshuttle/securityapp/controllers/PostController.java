package com.codingshuttle.securityapp.controllers;

import com.codingshuttle.securityapp.dtos.PostDto;
import com.codingshuttle.securityapp.entities.User;
import com.codingshuttle.securityapp.exceptions.ResourceNotFoundException;
import com.codingshuttle.securityapp.services.PostService;
import com.codingshuttle.securityapp.services.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;
    private final SessionService sessionService;

    public PostController(PostService postService, SessionService sessionService) {
        this.postService = postService;
        this.sessionService = sessionService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN') OR hasAuthority('POST_CREATE')")
    public ResponseEntity<PostDto> createNewPost(@RequestBody @Valid PostDto postDto){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       if(!sessionService.isSessionAvailableForUser(user)){
           throw new ResourceNotFoundException("Session Expired for the User : " + user.getEmail());
       }
        return ResponseEntity.ok(postService.createNewPost(postDto));
    }

    @GetMapping(path = "/post/{postId}")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PutMapping(path = "/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid PostDto postDto,@PathVariable(name = "postId") Long postId){
        return ResponseEntity.ok(postService.updatePost(postDto,postId));
    }
}
