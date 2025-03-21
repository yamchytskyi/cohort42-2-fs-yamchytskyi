package de.ait.homeworks.homework_13.controller;

import de.ait.homeworks.homework_13.model.Product;
import de.ait.homeworks.homework_13.service.OnlineShoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class OnlineStoreController {

    OnlineShoreService onlineShoreService;

    public OnlineStoreController(OnlineShoreService onlineShoreService) {
        this.onlineShoreService = onlineShoreService;
    }

    @GetMapping("/public/list")
    Iterable<Product> getListOfProducts() {
        return onlineShoreService.getListOfProducts();
    }

    @GetMapping("/customer/cart")
    String getCustomerCart() {
        return onlineShoreService.getCustomerCart();
    }

    @PostMapping("/manager/add{id}")
    String addNewProductToStore(@RequestParam Long id, @RequestParam String productName, @RequestParam double price) {
        return onlineShoreService.addNewProductToStore(id, productName, price);
    }
}
