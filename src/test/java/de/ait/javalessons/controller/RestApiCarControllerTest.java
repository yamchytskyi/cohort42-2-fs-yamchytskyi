package de.ait.javalessons.controller;

import de.ait.javalessons.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Класс для тестирования контроллера RestApiCarController.
// Class for testing the RestApiCarController.
public class RestApiCarControllerTest {

    // Экземпляр контроллера, который будет тестироваться.
    // Instance of the controller to be tested.
    private RestApiCarController restApiCarController;

    // Метод, который выполняется перед каждым тестом.
    // Здесь создается новый экземпляр контроллера.
    // Method that runs before each test.
    // Here, a new instance of the controller is created.
    @BeforeEach
    void setUp() {
        restApiCarController = new RestApiCarController();
    }

    // Тест для проверки метода получения списка автомобилей.
    // Проверяет, что возвращается 4 автомобиля, и первый автомобиль имеет имя "BMW M1".
    // Test for checking the method of getting a list of cars.
    // Checks that 4 cars are returned, and the first car has the name "BMW M1".
    @Test
    void testGetCarsReturnDefaultCars() {
        // Получаем список автомобилей в виде Iterable.
        // Get the list of cars as Iterable.
        Iterable<Car> resultCarsIterable = restApiCarController.getCars();
        // Преобразуем Iterable в List для удобства проверки.
        // Convert Iterable to List for easier verification.
        List<Car> resultCars = new ArrayList<>();
        resultCarsIterable.forEach(resultCars::add);

        // Проверяем, что размер списка равен 4.
        // Check that the size of the list is 4.
        assertEquals(4, resultCars.size());
        // Проверяем, что имя первого автомобиля равно "BMW M1".
        // Check that the name of the first car is "BMW M1".
        assertEquals("BMW M1", resultCars.get(0).getName());
    }

    // Тест для проверки метода получения автомобиля по ID.
    // Проверяет, что автомобиль с ID "1" существует и имеет имя "BMW M1".
    // Test for checking the method of getting a car by ID.
    // Checks that the car with ID "1" exists and has the name "BMW M1".
    @Test
    void testGetCarByIdWasFound() {
        // Получаем автомобиль по ID "1".
        // Get the car by ID "1".
        Optional<Car> result = restApiCarController.getCarById("1");
        // Проверяем, что автомобиль существует.
        // Check that the car exists.
        assertTrue(result.isPresent());
        // Проверяем, что имя автомобиля равно "BMW M1".
        // Check that the car's name is "BMW M1".
        assertEquals("BMW M1", result.get().getName());
    }

    // Тест для проверки случая, когда автомобиль с указанным ID не найден.
    // Проверяет, что Optional пустой.
    // Test for checking the case when a car with the specified ID is not found.
    // Checks that the Optional is empty.
    @Test
    void testGetCarByIdWasNotFound() {
        // Получаем автомобиль по ID "10".
        // Get the car by ID "10".
        Optional<Car> result = restApiCarController.getCarById("10");
        // Проверяем, что автомобиль не найден.
        // Check that the car is not found.
        assertFalse(result.isPresent());
    }

    // Тест для проверки метода добавления нового автомобиля.
    // Проверяет, что автомобиль успешно добавлен и имеет правильные данные.
    // Test for checking the method of adding a new car.
    // Checks that the car is successfully added and has the correct data.
    @Test
    void testPostCarAddNewCar() {
        // Создаем новый автомобиль для добавления.
        // Create a new car to add.
        Car carToAdd = new Car("5", "Tesla Model 1");
        // Добавляем автомобиль и получаем результат.
        // Add the car and get the result.
        Car result = restApiCarController.postCar(carToAdd);
        // Проверяем, что имя добавленного автомобиля равно "Tesla Model 1".
        // Check that the name of the added car is "Tesla Model 1".
        assertEquals("Tesla Model 1", result.getName());
        // Проверяем, что ID добавленного автомобиля равно "5".
        // Check that the ID of the added car is "5".
        assertEquals("5", result.getId());

        // Получаем обновленный список автомобилей.
        // Get the updated list of cars.
        Iterable<Car> resultCarsIterable = restApiCarController.getCars();
        List<Car> resultCars = new ArrayList<>();
        resultCarsIterable.forEach(resultCars::add);
        // Проверяем, что размер списка увеличился до 5.
        // Check that the size of the list has increased to 5.
        assertEquals(5, resultCars.size());
    }

    // Тест для проверки метода обновления информации об автомобиле.
    // Проверяет, что автомобиль с ID "1" успешно обновлен.
    // Test for checking the method of updating car information.
    // Checks that the car with ID "1" is successfully updated.
    @Test
    void testPutCarUpdateCarInfo() {
        // Создаем автомобиль с обновленными данными.
        // Create a car with updated data.
        Car carToAdd = new Car("1", "Tesla Model 1");
        // Обновляем автомобиль и получаем ответ.
        // Update the car and get the response.
        ResponseEntity<Car> responseEntityResult = restApiCarController.putCar("1", carToAdd);
        // Проверяем, что статус ответа равен 200 (OK).
        // Check that the response status is 200 (OK).
        assertEquals(200, responseEntityResult.getStatusCodeValue());
        // Проверяем, что имя обновленного автомобиля равно "Tesla Model 1".
        // Check that the name of the updated car is "Tesla Model 1".
        assertEquals("Tesla Model 1", responseEntityResult.getBody().getName());
    }

    // Тест для проверки случая, когда автомобиль с указанным ID не существует.
    // Проверяет, что создается новый автомобиль и возвращается статус 201 (Created).
    // Test for checking the case when a car with the specified ID does not exist.
    // Checks that a new car is created and the status 201 (Created) is returned.
    @Test
    void testPutCarCreateNewCar() {
        // Создаем новый автомобиль с ID "10".
        // Create a new car with ID "10".
        Car carToAdd = new Car("10", "Tesla Model 1");
        // Пытаемся обновить автомобиль, но так как его нет, он будет создан.
        // Try to update the car, but since it doesn't exist, it will be created.
        ResponseEntity<Car> responseEntityResult = restApiCarController.putCar("10", carToAdd);
        // Проверяем, что статус ответа равен 201 (Created).
        // Check that the response status is 201 (Created).
        assertEquals(201, responseEntityResult.getStatusCodeValue());
        // Проверяем, что имя созданного автомобиля равно "Tesla Model 1".
        // Check that the name of the created car is "Tesla Model 1".
        assertEquals("Tesla Model 1", responseEntityResult.getBody().getName());
    }

    // Тест для проверки метода удаления автомобиля.
    // Проверяет, что автомобиль с ID "1" успешно удален.
    // Test for checking the method of deleting a car.
    // Checks that the car with ID "1" is successfully deleted.
    @Test
    void testDeleteCarSuccess() {
        // Получаем список автомобилей до удаления.
        // Get the list of cars before deletion.
        Iterable<Car> resultCarsIterable = restApiCarController.getCars();
        List<Car> resultCars = new ArrayList<>();
        resultCarsIterable.forEach(resultCars::add);
        // Проверяем, что размер списка равен 4.
        // Check that the size of the list is 4.
        assertEquals(4, resultCars.size());

        // Удаляем автомобиль с ID "1".
        // Delete the car with ID "1".
        restApiCarController.deleteCar("1");

        // Получаем список автомобилей после удаления.
        // Get the list of cars after deletion.
        Iterable<Car> resultCarsIterableDeletedCar = restApiCarController.getCars();
        resultCars = new ArrayList<>();
        resultCarsIterableDeletedCar.forEach(resultCars::add);
        // Проверяем, что размер списка уменьшился до 3.
        // Check that the size of the list has decreased to 3.
        assertEquals(3, resultCars.size());
    }
}