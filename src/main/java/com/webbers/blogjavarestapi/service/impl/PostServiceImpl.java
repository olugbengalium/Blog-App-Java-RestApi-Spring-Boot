package com.webbers.blogjavarestapi.service.impl;

import com.webbers.blogjavarestapi.entity.Post;
import com.webbers.blogjavarestapi.exceptions.ResourceNotFoundException;
import com.webbers.blogjavarestapi.payload.PostDto;
import com.webbers.blogjavarestapi.payload.PostResponse;
import com.webbers.blogjavarestapi.repository.PostRepository;
import com.webbers.blogjavarestapi.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

//import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }



    @Override
    public PostDto createPost(PostDto postDto){
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToDto(newPost);
        // covert post(entity) to PostDto (dto)
        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> postList = posts.getContent();
        List<PostDto> content = postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(postResponse.isLast());

        return postResponse;
    }


    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id" ,id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(post.getContent());
        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }


//    @Override
//    public List<PostDto> getAllPost() {
//        List<Post> posts = postRepository.findAll();
//        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
//    }


// Get Pages by Pagination

//    @Override
//    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy) {
//        Pageable pageable= PageRequest.of(pageNo, pageSize);
//        Page<Post> posts = postRepository.findAll(pageable);
//
//        List<Post> postList = posts.getContent();
//        List<PostDto> content = postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
//
//        PostResponse postResponse = new PostResponse();
//        postResponse.setContent(content);
//        postResponse.setPageNo(posts.getNumber());
//        postResponse.setPageSize(posts.getSize());
//        postResponse.setTotalElements(posts.getTotalElements());
//        postResponse.setTotalPages(posts.getTotalPages());
//        postResponse.setLast(postResponse.isLast());
//
//        return postResponse;
//    }

}
