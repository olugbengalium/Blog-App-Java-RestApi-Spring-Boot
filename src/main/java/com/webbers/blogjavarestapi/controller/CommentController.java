package com.webbers.blogjavarestapi.controller;

import com.webbers.blogjavarestapi.payload.CommentDto;
import com.webbers.blogjavarestapi.service.CommentService;
import com.webbers.blogjavarestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//    @GetMapping("post/{postid}/comments")
//    @GetMapping("post/{postid}/comments")
//    public long thyyh(@PathVariable(value = "postid") long postId){
//        return postId;
//    }
    @PostMapping ("post/{postid}/comments")
    public ResponseEntity<CommentDto> createAnewPost(@PathVariable(value = "postid") long postId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("posts/{postid}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable(value = "postid") long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("posts/{postid}/comments/{commentid}")
    public CommentDto getCommentByID(@PathVariable(value = "postid") long postId,
                                     @PathVariable(value = "commentid") long commentid){
        return commentService.getCommentById(postId,commentid);
    }

    @PutMapping("posts/{postid}/comments/{commentid}")
    public ResponseEntity<CommentDto> updateAComment(@PathVariable(value = "postid") long postid,
                                     @PathVariable(value = "commentid") long commentid,
                                     @RequestBody CommentDto commentDto){
        CommentDto updatedComment = commentService.updateCommment(postid, commentid, commentDto);
        return new  ResponseEntity<>(updatedComment,HttpStatus.CREATED);
    }

    @DeleteMapping("posts/{postid}/comments/{commentid}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postid") long postid,
                                                @PathVariable(value = "commentid") long commentid){
        commentService.deleteComment(postid, commentid);
        return new ResponseEntity<>("Comment Deleted",HttpStatus.OK);
    }
}
