package com.webbers.blogjavarestapi.service;

import com.webbers.blogjavarestapi.entity.Comment;
import com.webbers.blogjavarestapi.payload.CommentDto;

public interface CommentService {
    Comment createPost(long postid,CommentDto commentDto);
}
