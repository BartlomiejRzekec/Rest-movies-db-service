package com.example.repository;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Movie;

@Service
@Transactional
public class MovieServiceImpl implements MovieService{
	
	
	
	private MoviesRepository moviesRepository;
	
	
	@Autowired
	public MovieServiceImpl(MoviesRepository moviesRepository) {
		this.moviesRepository = moviesRepository;
	}

	@Override
	public Movie findMovieByTitle(String title) {
		return moviesRepository.findByTitle(title);
	}

	@Override
	public List<Movie> findMoviesByGenre(String genre) {
		return moviesRepository.findAllByGenre(genre);
	}

	@Override
	public Movie saveMovie(Movie movie) {
		return moviesRepository.save(movie);
	}

	@Override
	public List<Movie> saveAllMovies(List<Movie> listofMovies) {
		return moviesRepository.saveAll(listofMovies);
	}

	@Override
	public List<Movie> findAllMovies() {
		return moviesRepository.findAll().stream()
										 .sorted((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()))
										 .collect(Collectors.toList());
	}

}
