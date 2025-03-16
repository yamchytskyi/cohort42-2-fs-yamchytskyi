package de.ait.javalessons.repositories;

import de.ait.javalessons.model.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FakeCarRepository implements CarRepository {
    private List<Car> cars = new ArrayList<>();

    @Override
    public <S extends Car> S save(S entity) {
        cars.add(entity);
        return entity;
    }

    @Override
    public <S extends Car> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(cars::add);
        return entities;
    }

    @Override
    public Optional<Car> findById(String id) {
        return cars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsById(String id) {
        return cars.stream().anyMatch(car -> car.getId().equals(id));
    }

    @Override
    public Iterable<Car> findAll() {
        return cars;
    }

    @Override
    public Iterable<Car> findAllById(Iterable<String> ids) {
        List<Car> result = new ArrayList<>();
        for (String id : ids) {
            findById(id).ifPresent(result::add);
        }
        return result;
    }

    @Override
    public long count() {
        return cars.size();
    }

    @Override
    public void deleteById(String id) {
        cars.removeIf(car -> car.getId().equals(id));
    }

    @Override
    public void delete(Car entity) {
        cars.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        for (String id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Car> entities) {
        // Convert Iterable to Collection
        List<Car> entitiesToDelete = new ArrayList<>();
        entities.forEach(entitiesToDelete::add);

        // Remove all entities from the list
        cars.removeAll(entitiesToDelete);
    }

    @Override
    public void deleteAll() {
        cars.clear();
    }
}