package com.webbers.blogjavarestapi.controller;
import com.webbers.blogjavarestapi.payload.PostDto;
import com.webbers.blogjavarestapi.payload.PostResponse;
import com.webbers.blogjavarestapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return postService.getAllPost(pageNo,pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getAllPostsByID(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto>  updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.updatePost(postDto,id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")

    public ResponseEntity<String> deletethePost(@PathVariable(name = "id") long id){
        postService.deletePost(id);
        return ResponseEntity.ok("Post invalidated, therefore deleted");
    }
}
