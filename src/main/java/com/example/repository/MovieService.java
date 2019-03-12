package com.example.repository;

import java.util.List;

import com.example.entity.Movie;

public interface MovieService {

	Movie findMovieByTitle(String title);
	
	List<Movie> findMoviesByGenre(String genre);
	
	Movie saveMovie(Movie movie);
	
	List<Movie> saveAllMovies(List<Movie> listOfMovies);
	
	List<Movie> findAllMovies();
	
}
