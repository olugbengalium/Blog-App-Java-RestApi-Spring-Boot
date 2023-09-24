package com.webbers.blogjavarestapi.payload;

import com.webbers.blogjavarestapi.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;

    //title more than 2  characters
    //not null

    @NotEmpty
    @Size(min = 2, message = "post title should have more than 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "post description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments;
}
