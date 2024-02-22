package com.example.jwtauth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class JobPostRequestdto {
    private String description;
    private String title;
}
