package de.ait.javalessons.controller;

import de.ait.javalessons.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestApiMovieControllerTest {

    private RestApiMovieController restApiMovieController;

    @BeforeEach
    void setUp() {
        restApiMovieController = new RestApiMovieController();
    }

    @Test
    void getMoviesTestDefaultMovies() {
        Iterable<Movie> iterableMovies = restApiMovieController.getMovies();
        List<Movie> moviesList = new ArrayList<>();
        iterableMovies.forEach(moviesList::add);

        assertEquals(4, moviesList.size());

        assertEquals("Alien", moviesList.get(0).getTitle());
    }
    @Test
    void getMovieByIdTestCarWasFound() {
        Optional<Movie> result = restApiMovieController.getMovieById(1);

        assertTrue(result.isPresent());

        assertEquals("Alien", result.get().getTitle());
    }

    @Test
    void getMovieByIdTestCarWasNotFound() {
        Optional<Movie> result = restApiMovieController.getMovieById(0);

        assertFalse(result.isPresent());
    }

    @Test
    void addMovieTest() {
        Movie movieToAdd = new Movie(5, "Matrix", "Sci-fi", 1999);
        restApiMovieController.addMovie(movieToAdd);

        Iterable<Movie> movieIterable = restApiMovieController.getMovies();
        List<Movie> movieList = new ArrayList<>();
        movieIterable.forEach(movieList::add);

        assertEquals(5, movieList.size());
        assertEquals(movieToAdd.getId(), movieList.get(4).getId());
        assertEquals(movieToAdd.getTitle(), movieList.get(4).getTitle());
    }

    @Test
    void deleteMovieByIdTestExistingMovie() {
        String response = restApiMovieController.deleteMovieById(1);
        assertEquals("The movie has been deleted...", response);

        Iterable<Movie> movieIterable = restApiMovieController.getMovies();
        List<Movie> movieList = new ArrayList<>();
        movieIterable.forEach(movieList::add);

        assertEquals(3, movieList.size());
    }

    @Test
    void deleteMovieByIdTestNotExistingMovie() {
        String response = restApiMovieController.deleteMovieById(0);
        assertEquals("The movie doesn't exist...", response);

        Iterable<Movie> movieIterable = restApiMovieController.getMovies();
        List<Movie> movieList = new ArrayList<>();
        movieIterable.forEach(movieList::add);

        assertEquals(4, movieList.size());
    }
}
