package com.codingshuttle.prod_ready_features.controllers;

import com.codingshuttle.prod_ready_features.dtos.PostDto;
import com.codingshuttle.prod_ready_features.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
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
