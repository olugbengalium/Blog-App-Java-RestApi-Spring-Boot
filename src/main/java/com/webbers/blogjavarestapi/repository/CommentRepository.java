package com.webbers.blogjavarestapi.repository;

import com.webbers.blogjavarestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
