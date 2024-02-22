package com.example.jwtauth.service.Impl;

import com.example.jwtauth.dto.JobPostRequestdto;
import com.example.jwtauth.dto.JobPostResponseDto;
import com.example.jwtauth.model.JobPost;
import com.example.jwtauth.model.Status;
import com.example.jwtauth.model.User;
import com.example.jwtauth.repository.JobPostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Getter
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    public JobPostResponseDto saveJobPost(JobPostRequestdto jobPost) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        JobPost job = jobPostRepository.save(
                new JobPost(
                        userDetails,
                        jobPost.getTitle(),
                        jobPost.getDescription(),
                        Status.INACTIVE ));
        return JobPostResponseDto
                .builder()
                .id(job.getId())
                .status(job.getStatus())
                .title(job.getTitle())
                .description(job.getDescription())
                .user(job.getUser())
                .build();
    }

    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }

    public JobPost changeStatus(Long postId) throws Exception {
        JobPost jobPost = jobPostRepository.findById(postId)
                .orElseThrow(() -> new Exception("Job post not found with id: " + postId));
        if(jobPost.getStatus()==Status.ACTIVE){
            jobPost.setStatus(Status.INACTIVE);
        }else {
            jobPost.setStatus(Status.ACTIVE);
        }
        return jobPostRepository.save(jobPost);
    }
}
