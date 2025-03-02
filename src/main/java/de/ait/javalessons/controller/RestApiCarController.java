package de.ait.javalessons.controller;

import de.ait.javalessons.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class RestApiCarController {

    private List<Car> carList = new ArrayList<>();

    public RestApiCarController() {
        carList.addAll(List.of(
                new Car("1", "BMW M1"),
                new Car("2", "Audi A8"),
                new Car("3", "Kia Spartage"),
                new Car("4", "Volvo 960")
        ));
    }

//    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    // The same below
    @GetMapping()
    Iterable<Car> getCars() {
        return carList;
    }

    @GetMapping("/{id}")
    Optional<Car> getCarById(@PathVariable String id) {
        for (Car car : carList) {
            if(car.getId().equals(id)) {
                return  Optional.of(car);
            }
        }
        return Optional.empty();
    }

    @PostMapping()
    Car postCar(@RequestBody Car car) {
        carList.add(car);
        return car;
    }

    @PutMapping("/{id}")
    Car putCar(@RequestBody String id, @RequestBody Car car) {
        int index = -1;
        for(Car carToFind : carList) {
            if(carToFind.getId().equals(id)) {
                index = carList.indexOf(carToFind);
                carList.set(index, car);
            }
        }
        return (index == -1 ? postCar(car) : carList.get(index));

//        return (index == -1) ?
//            new ResponseEntity<>(postCar(car), HttpStatus.CREATED):
//            new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteCar(@PathVariable String id) {
        carList.removeIf(car -> car.getId().equals(id));
    }
}
