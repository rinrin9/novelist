package com.example.novelist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.novelist.entity.Topic;
import com.example.novelist.entity.User;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Iterable<Topic> findAllByOrderByUpdatedAtDesc();
    
    Optional<Topic> findById(Long id);
}