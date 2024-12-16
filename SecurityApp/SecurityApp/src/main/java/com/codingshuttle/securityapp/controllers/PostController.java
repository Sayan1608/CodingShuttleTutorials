package com.codingshuttle.securityapp.controllers;

import com.codingshuttle.securityapp.dtos.PostDto;
import com.codingshuttle.securityapp.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<PostDto> createNewPost(@RequestBody @Valid PostDto postDto){
        return ResponseEntity.ok(postService.createNewPost(postDto));
    }

    @GetMapping(path = "/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PutMapping(path = "/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid PostDto postDto,@PathVariable(name = "postId") Long postId){
        return ResponseEntity.ok(postService.updatePost(postDto,postId));
    }
}
