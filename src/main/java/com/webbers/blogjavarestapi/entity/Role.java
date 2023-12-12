package com.webbers.blogjavarestapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@RedisHash("AntiHeroine")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false)
    private String name;
}
