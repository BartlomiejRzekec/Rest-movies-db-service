package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Movie;

public interface MoviesRepository extends JpaRepository<Movie, Integer>{

	Movie findByTitle(String title);
	
	List<Movie> findAllByGenre(String genre);
	
}
