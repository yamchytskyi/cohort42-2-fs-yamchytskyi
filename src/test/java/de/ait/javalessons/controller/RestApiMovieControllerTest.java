package de.ait.javalessons.controller;

import de.ait.javalessons.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void getMovieByIdTestWasFound() {
        ResponseEntity<Movie> result = restApiMovieController.getMovieById(1);

        // Check that the status code is 200 OK
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // Check the content of the body
        assertNotNull(result.getBody());
        assertEquals("Alien", result.getBody().getTitle());
    }

    @Test
    void getMovieByIdTestWasNotFound() {
        ResponseEntity<Movie> result = restApiMovieController.getMovieById(0);

        // Check that the status code is 404 NOT FOUND
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        // Check the body is null since the movie doesn't exist
        assertNull(result.getBody());
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
        ResponseEntity<String> response = restApiMovieController.deleteMovieById(1);

        // Assert the response body contains the deletion message
        assertEquals("The movie has been deleted...", response.getBody());


    }
}