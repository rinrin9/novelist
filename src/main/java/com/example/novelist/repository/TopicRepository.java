package com.example.novelist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.novelist.entity.Topic;
import com.example.novelist.entity.User;
import com.example.novelist.entity.UserInf;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Iterable<Topic> findAllByOrderByUpdatedAtDesc();
    
    Iterable<Topic> findTop3ByOrderByUpdatedAtDesc();
    
    Optional<Topic> findById(Long id);
    
    public List<Topic> findByUserIdOrderByUpdatedAtDesc(Long userid);

    Iterable<Topic> findByTitleLike(String title);
}