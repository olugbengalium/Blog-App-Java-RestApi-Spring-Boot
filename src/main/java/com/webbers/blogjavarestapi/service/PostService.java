package com.webbers.blogjavarestapi.service;

import com.webbers.blogjavarestapi.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPost();
}
