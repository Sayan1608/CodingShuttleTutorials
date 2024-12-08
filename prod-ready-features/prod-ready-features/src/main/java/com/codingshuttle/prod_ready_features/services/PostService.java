package com.codingshuttle.prod_ready_features.services;

import com.codingshuttle.prod_ready_features.dtos.PostDto;
import com.codingshuttle.prod_ready_features.entities.PostEntity;
import com.codingshuttle.prod_ready_features.exceptions.ResourceNotFoundException;
import com.codingshuttle.prod_ready_features.repositories.PostRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
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
}
