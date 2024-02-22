package com.example.jwtauth.controllers.auth;

import com.example.jwtauth.dto.JobPostRequestdto;
import com.example.jwtauth.dto.JobPostResponseDto;
import com.example.jwtauth.model.JobPost;
import com.example.jwtauth.service.Impl.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/jobs")
public class JobController {
    private final JobPostService jobPostService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobPost> status(@PathVariable Long id) throws Exception {
        return  ResponseEntity.ok(jobPostService.changeStatus(id));
    }
    @PostMapping
    public ResponseEntity<JobPostResponseDto> saveJob(@RequestBody JobPostRequestdto dto){
        return ResponseEntity.ok(jobPostService.saveJobPost(dto));
    }
    @GetMapping
    public ResponseEntity<List<JobPost>> getAllJobs(){
        return ResponseEntity.ok(jobPostService.getAllJobPosts());
    }
}
