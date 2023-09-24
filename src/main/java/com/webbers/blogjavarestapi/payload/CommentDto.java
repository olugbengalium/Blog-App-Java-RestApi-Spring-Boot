package com.webbers.blogjavarestapi.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class CommentDto {
    private long id;
    private String name;
    @NotNull
    private String email;

    private String body;
}
