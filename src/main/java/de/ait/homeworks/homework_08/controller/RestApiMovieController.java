package de.ait.homeworks.homework_08.controller;

import de.ait.homeworks.homework_08.model.Movie;
import de.ait.homeworks.homework_08.repo.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class RestApiMovieController {

    private final MovieRepository movieRepository;

    public RestApiMovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        movieRepository.saveAll(List.of(
                new Movie(1, "Alien", "Sci-fi", 1979),
                new Movie(2, "The Thing", "Horror", 1982),
                new Movie(3, "Terminator", "Sci-fi", 1984),
                new Movie(4, "Mad Max", "Action", 1979)
        ));
    }

    @GetMapping()
    Iterable<Movie> getMovies() {
        return movieRepository.findAll();
    }

    // what's going on?
    @GetMapping("/{id}")
    ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        return movieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);

    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteMovieById(@PathVariable int id) {
                if(movieRepository.existsById(id)) {
                    movieRepository.deleteById(id);
                    return ResponseEntity.ok("The movie with id " + id + " has been deleted...");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with id " + id + "has not found");
                }
    }
}
