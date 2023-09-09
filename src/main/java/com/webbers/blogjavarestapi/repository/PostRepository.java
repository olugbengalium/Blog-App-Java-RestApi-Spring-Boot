package com.webbers.blogjavarestapi.repository;

import com.webbers.blogjavarestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
