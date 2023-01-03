package com.example.novelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.novelist.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}