package com.example.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
