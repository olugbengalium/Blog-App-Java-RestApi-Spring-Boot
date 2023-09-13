package com.webbers.blogjavarestapi.payload;

import jakarta.persistence.Column;

public class CommentDto {
    private long id;
    private String name;

    private String email;

    private String body;
}
