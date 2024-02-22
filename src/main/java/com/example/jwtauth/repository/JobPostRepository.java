package com.example.jwtauth.repository;

import com.example.jwtauth.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPostRepository extends JpaRepository<JobPost,Long> {
}
