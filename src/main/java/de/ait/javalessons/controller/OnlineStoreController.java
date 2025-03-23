package de.ait.javalessons.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class OnlineStoreController {

    @GetMapping("/public/list")
    public ResponseEntity<String> getListOfProducts() {
        return ResponseEntity.ok("Apple, Banana, Mango, Orange");
    }

    @GetMapping("/customer/cart")
    public ResponseEntity<String> getCustomerCart() {
        return ResponseEntity.ok( "Apple: 3 kg, Banana: 2 kg");
    }

    @GetMapping("/manager/add")
    public ResponseEntity<String> addProductToTheStore() {
        return ResponseEntity.ok("What product do you want to add to the store?");
    }

}
