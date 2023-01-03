package com.example.novelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.novelist.entity.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Iterable<Topic> findAllByOrderByUpdatedAtDesc();
}