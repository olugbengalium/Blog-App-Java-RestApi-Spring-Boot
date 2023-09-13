package com.webbers.blogjavarestapi.service.impl;

import com.webbers.blogjavarestapi.entity.Comment;
import com.webbers.blogjavarestapi.payload.CommentDto;
import com.webbers.blogjavarestapi.repository.CommentRepository;
import com.webbers.blogjavarestapi.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createPost(long postid, CommentDto commentDto) {
        return null;
    }
}
