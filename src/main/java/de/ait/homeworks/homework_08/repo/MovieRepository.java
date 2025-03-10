package de.ait.homeworks.homework_08.repo;

import de.ait.homeworks.homework_08.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
}
