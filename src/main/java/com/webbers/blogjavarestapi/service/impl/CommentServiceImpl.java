package com.webbers.blogjavarestapi.service.impl;

import com.webbers.blogjavarestapi.entity.Comment;
import com.webbers.blogjavarestapi.entity.Post;
import com.webbers.blogjavarestapi.exceptions.BlogAPIException;
import com.webbers.blogjavarestapi.exceptions.ResourceNotFoundException;
import com.webbers.blogjavarestapi.payload.CommentDto;
import com.webbers.blogjavarestapi.repository.CommentRepository;
import com.webbers.blogjavarestapi.repository.PostRepository;
import com.webbers.blogjavarestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postid, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post","id",postid));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long id) {
        List<Comment> comments = commentRepository.findByPostId(id);
        return comments.stream().map((comment) -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postid, long commentid) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post","id", postid));
        Comment comment = commentRepository.findById(commentid).orElseThrow(()-> new ResourceNotFoundException("comment","id", commentid));
        if (comment.getPost().getId() != (post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment doesn't belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommment(long postid, long commentid, CommentDto commentObject) {
        Post post = postRepository.findById(postid).orElseThrow(()-> new ResourceNotFoundException("post","id",postid));
        Comment comment = commentRepository.findById(commentid).orElseThrow(()-> new ResourceNotFoundException("comment with","id",commentid));
        if (comment.getPost().getId() != (post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment doesn't belong to post");
        }
        comment.setBody(commentObject.getBody());
        comment.setName(commentObject.getName());
        comment.setEmail(comment.getEmail());
        commentRepository.save(comment);

        return mapToDto(comment);
    }

    @Override
    public void deleteComment(long postid, long commentid) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post","id", postid));
        Comment comment = commentRepository.findById(commentid).orElseThrow(()-> new ResourceNotFoundException("comment","id", commentid));
        if (comment.getPost().getId() != (post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment doesn't belong to post");
        }
        commentRepository.delete(comment);
    }


    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }
//    private CommentDto mapToDto(Comment comment){
//        CommentDto commentDto = new CommentDto();
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setName(comment.getName());
//        commentDto.setId(comment.getId());
//        return commentDto;
//    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }

//    private Comment mapToEntity(CommentDto commentDto){
//        Comment comment = new Comment();
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setName(commentDto.getName());
//        comment.setId(commentDto.getId());
//        return comment;
//    }
}
