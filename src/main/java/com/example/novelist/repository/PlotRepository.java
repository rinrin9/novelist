package com.example.novelist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.novelist.entity.Plot;

public interface PlotRepository extends JpaRepository<Plot, Long> {
	
	Optional<Plot> findByTopicId(Long id);
	
}
