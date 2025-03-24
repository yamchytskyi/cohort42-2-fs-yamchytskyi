package de.ait.javalessons.controller;

import de.ait.javalessons.model.Car;
import de.ait.javalessons.repositories.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j // Аннотация Lombok для автоматического создания логгера
// @Slf4j - Lombok annotation for automatic logger creation
@RestController // Аннотация Spring, указывающая, что этот класс является REST-контроллером
// @RestController - Spring annotation indicating that this class is a REST controller
@RequestMapping("/cars") // Базовый путь для всех методов в этом контроллере
// @RequestMapping("/cars") - Base path for all methods in this controller
public class RestApiCarController {

    private final CarRepository carRepository; // Репозиторий для работы с автомобилями
    // private final CarRepository carRepository; - Repository for working with cars

    // Конструктор класса, инициализирующий список автомобилей
    // Class constructor initializing the list of cars
    public RestApiCarController(CarRepository carRepository) {
        this.carRepository = carRepository;

        this.carRepository.saveAll(
                List.of(
                        new Car("1", "BMW M1"),
                        new Car("2", "Audi A8"),
                        new Car("3", "Kia Spartage"),
                        new Car("4", "Volvo 960")
                ));
    }

    /**
     * Метод для получения всех автомобилей.
     * GET-запрос на /cars
     *
     * @return список всех автомобилей
     */
    // Method to get all cars.
    // GET request to /cars
    //
    // @return list of all cars
    @GetMapping
    Iterable<Car> getCars() {
        return carRepository.findAll();
    }

    /**
     * Метод для получения автомобиля по его ID.
     * GET-запрос на /cars/{id}
     *
     * @param id идентификатор автомобиля
     * @return Optional, содержащий автомобиль, если он найден, иначе пустой Optional
     */
    // Method to get a car by its ID.
    // GET request to /cars/{id}
    //
    // @param id car identifier
    // @return Optional containing the car if found, otherwise an empty Optional
    @GetMapping("/{id}")
    Optional<Car> getCarById(@PathVariable String id) {
        Optional<Car> carInDatabase = carRepository.findById(id);
        if (carInDatabase.isPresent()) {
            log.info("Car with id {} was found", id);
            return carInDatabase;
        }

        log.info("Car with id {} was not found", id);
        return Optional.empty();
    }

    /**
     * Метод для добавления нового автомобиля.
     * POST-запрос на /cars
     *
     * @param car объект автомобиля, переданный в теле запроса
     * @return добавленный автомобиль
     */
    // Method to add a new car.
    // POST request to /cars
    //
    // @param car car object passed in the request body
    // @return added car
    @PostMapping
    Car postCar(@RequestBody Car car) {
        Car saveResult = carRepository.save(car);
        return saveResult;
    }

    /**
     * Метод для обновления существующего автомобиля по его ID.
     * PUT-запрос на /cars/{id}
     *
     * @param id  идентификатор автомобиля, который нужно обновить
     * @param car объект автомобиля с новыми данными
     * @return ResponseEntity с обновлённым автомобилем и статусом OK, если автомобиль найден,
     *         или с новым автомобилем и статусом CREATED, если автомобиль не найден
     */
    // Method to update an existing car by its ID.
    // PUT request to /cars/{id}
    //
    // @param id identifier of the car to be updated
    // @param car car object with new data
    // @return ResponseEntity with the updated car and status OK if the car is found,
    //         or with a new car and status CREATED if the car is not found
    @PutMapping("/{id}")
    ResponseEntity<Car> putCar(@PathVariable String id, @RequestBody Car car) {
        if(carRepository.existsById(id)){
            Car carToUpdate = carRepository.findById(id).get();
            carToUpdate.setName(car.getName());
            carToUpdate.setId(id);
            Car savedCar = carRepository.save(carToUpdate);
            return new ResponseEntity<>(savedCar, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(postCar(car), HttpStatus.CREATED);
        }
    }

    /**
     * Метод для удаления автомобиля по его ID.
     * DELETE-запрос на /cars/{id}
     *
     * @param id идентификатор автомобиля, который нужно удалить
     */
    // Method to delete a car by its ID.
    // DELETE request to /cars/{id}
    //
    // @param id identifier of the car to be deleted
    @DeleteMapping("/{id}")
    void deleteCar(@PathVariable String id) {
        carRepository.deleteById(id);
    }
}