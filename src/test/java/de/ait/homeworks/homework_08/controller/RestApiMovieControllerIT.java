package de.ait.homeworks.homework_08.controller;

import de.ait.homeworks.homework_08.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Integration tests for the MovieController class.
 * The @SpringBootTest annotation indicates that this is an integration test.
 * webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT selects a random port for the application.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiMovieControllerIT {

    /**
     * The @Autowired annotation automatically injects the TestRestTemplate dependency.
     * TestRestTemplate is used to perform HTTP requests in integration tests.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    // Base URL for the movies API
    private static final String BASE_URL = "/movies";

    @Test
    void testGetMovies() {
        // Send a GET request to retrieve all movies
        ResponseEntity<Movie[]> response = testRestTemplate.getForEntity(BASE_URL, Movie[].class);

        // Assert the response status code is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert the response contains 4 movies
        assertEquals(4, response.getBody().length);

        // Assert the first movie's title is "Alien"
        assertEquals("Alien", response.getBody()[0].getTitle());
    }

    @Test
    void testGetMovieByIdWasFound() {
        // Send a GET request to retrieve a movie by ID
        ResponseEntity<Movie> response = testRestTemplate.getForEntity(BASE_URL + "/1", Movie.class);

        // Assert the response status code is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert the movie ID is 1
        assertEquals(1, response.getBody().getId());

        // Assert the movie title is "Alien"
        assertEquals("Alien", response.getBody().getTitle());
    }

    @Test
    void testGetMovieByIdWasNotFound() {
        // Send a GET request to retrieve a movie with an invalid ID
        ResponseEntity<Movie> response = testRestTemplate.getForEntity(BASE_URL + "/-1", Movie.class);

        // Assert the response status code is NOT_FOUND (404)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Assert the response body is null
        assertNull(response.getBody());
    }

    @Test
    void testAddMovie() {
        // Create a new movie to add
        Movie movieToAdd = new Movie(5, "Matrix", "Sci-fi", 1999);

        // Send a POST request to add the movie
        ResponseEntity<Movie> response = testRestTemplate.postForEntity(BASE_URL, movieToAdd, Movie.class);

        // Assert the response status code is CREATED (201)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Assert the added movie has the correct ID
        assertEquals(5, response.getBody().getId());

        // Assert the added movie has the correct title
        assertEquals("Matrix", response.getBody().getTitle());
    }

    @Test
    void testDeleteMovieById() {
        // ID of the movie to delete
        String movieId = "/5";

        // Send a DELETE request to delete the movie
        ResponseEntity<String> response = testRestTemplate.exchange(
                BASE_URL + movieId,
                HttpMethod.DELETE,
                null,
                String.class
        );

        // Assert the response status code is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert the response body contains the correct message
        assertEquals("The movie with id " + movieId + " has been deleted...", response.getBody());
    }

    @Test
    void testDeleteMovieByIdMovieNotFound() {
        // ID of a movie that does not exist
        String falseMovieId = "/-1";

        // Send a DELETE request to delete a non-existent movie
        ResponseEntity<String> response = testRestTemplate.exchange(
                BASE_URL + falseMovieId,
                HttpMethod.DELETE,
                null,
                String.class
        );

        // Assert the response status code is NOT_FOUND (404)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Assert the response body contains the correct error message
        assertEquals("Movie with id " + falseMovieId + " has not been found", response.getBody());
    }
}