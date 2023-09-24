package com.webbers.blogjavarestapi.service;

import com.webbers.blogjavarestapi.entity.Comment;
import com.webbers.blogjavarestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postid,CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long id);
    CommentDto getCommentById(long postid, long commentid);
    CommentDto updateCommment(long postid, long commentid, CommentDto commentObject);

    void deleteComment(long postid, long commentid);
}
