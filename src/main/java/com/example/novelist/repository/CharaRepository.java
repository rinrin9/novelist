package com.example.novelist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.novelist.entity.Chara;

public interface CharaRepository extends JpaRepository<Chara, Long> {
	
	Optional<Chara> findByTopicIdAndRole(Long id, String role);
	
	Iterable<Chara> findAllOrderByTopicId(Long id);
	
}
