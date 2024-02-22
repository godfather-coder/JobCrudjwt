package com.example.jwtauth.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job-posts")
@Data
@NoArgsConstructor
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;

    public JobPost(User user, String title, String description, Status status) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
