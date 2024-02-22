package com.example.jwtauth.dto;

import com.example.jwtauth.model.Status;
import com.example.jwtauth.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class JobPostResponseDto {
    private Long id;
    private User user;
    private String description;
    private String title;
    private Status status;
}
