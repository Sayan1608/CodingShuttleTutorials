package com.codingshuttle.securityapp.services;


import com.codingshuttle.securityapp.dtos.PostDto;
import com.codingshuttle.securityapp.entities.PostEntity;
import com.codingshuttle.securityapp.entities.User;
import com.codingshuttle.securityapp.exceptions.ResourceNotFoundException;
import com.codingshuttle.securityapp.repositories.PostRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public PostDto createNewPost(@Valid PostDto postDto) {
        PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }

    private void isExistsPostById(Long postId){
        if(!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post not found with id : " + postId);
    }

    public PostDto getPostById(Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user {} : ",user);
        isExistsPostById(postId);
        PostEntity postEntity = postRepository.findById(postId).get();
        return modelMapper.map(postEntity, PostDto.class);
    }

    public PostDto updatePost(@Valid PostDto newPost, Long postId) {
        isExistsPostById(postId);
        PostEntity olderPost = postRepository.findById(postId).get();
        newPost.setId(postId);
        modelMapper.map(newPost,olderPost);
        return modelMapper.map(postRepository.save(olderPost), PostDto.class);
    }

    public List<PostDto> getAllPosts() {
        List<PostEntity> posts = postRepository.findAll();
        return posts
                .stream()
                .map(e->modelMapper.map(e, PostDto.class))
                .collect(Collectors.toList());
    }
}
