package com.example.repository;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.entity.Movie;
import com.example.error.MovieNotFoundException;

@RestController
@RequestMapping("/movies")
public class DbServiceResource {
	
	private MovieService movieService;

	@Autowired
	public DbServiceResource(MovieService movieService) {
		this.movieService = movieService;
	}
	
	@ExceptionHandler(MovieNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Error movieNotFound(MovieNotFoundException e) {
		
		String value = e.getValue();
		
		Error error = new Error("Movie [ " + value + " ] not found");
		
		return error;
	}

	
	@GetMapping(value = "/findMovie/{title}")
	@ResponseStatus(code = HttpStatus.FOUND)
	public Movie findMovieByTitle(@PathVariable String title) {
		
		Movie movie = movieService.findMovieByTitle(title);
	
		if(movie == null) {
			throw new MovieNotFoundException(title);
		}
		return movie;
	}
	
	@GetMapping("/findByGenre/{genre}")
	@ResponseStatus(code = HttpStatus.FOUND)
	public List<Movie> findMoviesByGenre(@PathVariable String genre){
		
		List<Movie> movies = movieService.findMoviesByGenre(genre);
		
		if(movies.isEmpty()) {
			throw new MovieNotFoundException(genre);
		}
		return movies;
	}
	
	
	@GetMapping("/findAll")
	public List<Movie> findAllMovies(){
		return movieService.findAllMovies();
	}
	
	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie, UriComponentsBuilder ucb) {
		
		Movie movieToSave = movieService.saveMovie(movie);
		
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/movies/findMovie/")
							 .path(movieToSave.getTitle())
							 .build()
							 .toUri();
		
		headers.setLocation(locationUri);
		
		return new ResponseEntity<>(movieToSave, headers, HttpStatus.CREATED);
		
	}
	
	@PostMapping(value = "/saveMovies", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Movie>> saveMovies(@RequestBody List<Movie> listOfMovies, UriComponentsBuilder ucb){
		
		List<Movie> moviesToSave = movieService.saveAllMovies(listOfMovies);
		
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/movies/findAll")
							 .build()
							 .toUri();
		
		headers.setLocation(locationUri);
		
		return new ResponseEntity<>(moviesToSave, headers, HttpStatus.CREATED);
	}
	
	

}
