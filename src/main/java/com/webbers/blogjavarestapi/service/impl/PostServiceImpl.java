package com.webbers.blogjavarestapi.impl;

import com.webbers.blogjavarestapi.entity.Post;
import com.webbers.blogjavarestapi.payload.PostDto;
import com.webbers.blogjavarestapi.repository.PostRepository;
import com.webbers.blogjavarestapi.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }



    @Override
    public PostDto createPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost = postRepository.save(post);

        // covert post(entity) to PostDto (dto)

        PostDto newPostResponse = new PostDto();
        newPostResponse.setTitle(post.getTitle());
        newPostResponse.setDescription(post.getDescription());
        newPostResponse.setContent(post.getContent());
        return null;
    }
}
