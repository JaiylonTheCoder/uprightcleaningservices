package com.jaiylonbabb.uprightcleaningservices.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 10000)
    private String body;

    private String creatorName;

    private LocalDateTime createdAt;

    private String imageUrl;
}
