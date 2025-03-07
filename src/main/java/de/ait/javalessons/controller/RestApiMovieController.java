package de.ait.javalessons.controller;

import de.ait.javalessons.model.Movie;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class RestApiMovieController {

    private List<Movie> moviesList = new ArrayList<>();

    public RestApiMovieController() {
        moviesList.addAll(List.of(
                new Movie(1, "Alien", "Sci-fi", 1979),
                new Movie(2, "The Thing", "Horror", 1982),
                new Movie(3, "Terminator", "Sci-fi", 1984),
                new Movie(4, "Mad Max", "Action", 1979)
        ));
    }

    @GetMapping()
    Iterable<Movie> getMovies() {
        return moviesList;
    }

    @GetMapping("/{id}")
    Optional<Movie> getMovieById(@PathVariable int id) {
        for (Movie movie : moviesList) {
            if(movie.getId() == (id)) {
                return Optional.of(movie);
            }
        }
        return Optional.empty();
    }

    @PostMapping
    String addMovie(@RequestBody Movie movie) {
        moviesList.add(movie);
        return "Success...";
    }

    @DeleteMapping("/{id}")
    String deleteMovieById(@PathVariable int id) {
        for (Movie movie : moviesList) {
            if(movie.getId() == (id)) {
                moviesList.remove(movie);
                return "The movie has been deleted...";
            }
        }
        return "The movie doesn't exist...";
    }
}
