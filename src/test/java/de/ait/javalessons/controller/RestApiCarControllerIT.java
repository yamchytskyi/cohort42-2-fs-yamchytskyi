package de.ait.javalessons.controller;

import de.ait.javalessons.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Аннотация @SpringBootTest указывает, что это интеграционный тест, который запускает Spring Boot приложение.
// webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT означает, что приложение будет запущено на случайном порту.
// The @SpringBootTest annotation indicates that this is an integration test that runs a Spring Boot application.
// webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT means that the application will run on a random port.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiCarControllerIT {

    // Аннотация @Autowired автоматически внедряет (инжектирует) экземпляр TestRestTemplate.
    // TestRestTemplate используется для выполнения HTTP-запросов к тестируемому приложению.
    // The @Autowired annotation automatically injects an instance of TestRestTemplate.
    // TestRestTemplate is used to perform HTTP requests to the application under test.
    @Autowired
    private TestRestTemplate testRestTemplate;

    // Базовый URL для всех запросов к API автомобилей.
    // The base URL for all requests to the cars API.
    private static final String BASE_URL = "/cars";

    // Метод, который выполняется перед каждым тестом. В данном случае он пустой, но может быть использован для инициализации данных.
    // A method that runs before each test. In this case, it is empty but can be used for data initialization.
    @BeforeEach
    void setUp() {

    }

    // Тест для проверки метода получения списка автомобилей.
    // Проверяет, что возвращается статус OK и что в ответе содержится 4 автомобиля.
    // Также проверяет, что первый автомобиль в списке имеет имя "BMW M1".
    // Test for checking the method of getting a list of cars.
    // Checks that the status OK is returned and that the response contains 4 cars.
    // Also checks that the first car in the list has the name "BMW M1".
    @Test
    void testGetCarsReturnDefaultCars() {
        ResponseEntity<Car[]> response = testRestTemplate.getForEntity(BASE_URL, Car[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, response.getBody().length);
        assertEquals("BMW M1", response.getBody()[0].getName());
    }

    // Тест для проверки метода получения автомобиля по ID.
    // Проверяет, что возвращается статус OK и что автомобиль с ID "1" имеет имя "BMW M1".
    // Test for checking the method of getting a car by ID.
    // Checks that the status OK is returned and that the car with ID "1" has the name "BMW M1".
    @Test
    void testGetCarByIdWasFound() {
        ResponseEntity<Car> response = testRestTemplate.getForEntity(BASE_URL + "/1", Car.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("BMW M1", response.getBody().getName());
        assertEquals("1", response.getBody().getId());
    }

    // Тест для проверки случая, когда автомобиль с указанным ID не найден.
    // Проверяет, что возвращается статус OK и что тело ответа равно null.
    // Test for checking the case when a car with the specified ID is not found.
    // Checks that the status OK is returned and that the response body is null.
    @Test
    void testGetCarByIdWasNotFound() {
        ResponseEntity<Car> response = testRestTemplate.getForEntity(BASE_URL + "/10", Car.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    // Тест для проверки метода добавления нового автомобиля.
    // Проверяет, что возвращается статус OK и что добавленный автомобиль имеет имя "Tesla Model S" и ID "5".
    // Test for checking the method of adding a new car.
    // Checks that the status OK is returned and that the added car has the name "Tesla Model S" and ID "5".
    @Test
    void testPostCarAddNewCar() {
        Car carToAdd = new Car("5", "Tesla Model S");
        ResponseEntity<Car> response = testRestTemplate.postForEntity(BASE_URL, carToAdd, Car.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tesla Model S", response.getBody().getName());
        assertEquals("5", response.getBody().getId());
    }

    // Тест для проверки метода обновления информации об автомобиле.
    // Проверяет, что возвращается статус OK и что обновленный автомобиль имеет имя "Tesla Model S".
    // Test for checking the method of updating car information.
    // Checks that the status OK is returned and that the updated car has the name "Tesla Model S".
    @Test
    void testPutCarUpdateCarInfo() {
        Car carToUpdate = new Car("1", "Tesla Model S");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Car> request = new HttpEntity<>(carToUpdate, headers);
        ResponseEntity<Car> response = testRestTemplate.exchange(BASE_URL + "/1", HttpMethod.PUT, request, Car.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tesla Model S", response.getBody().getName());
    }
}