package de.ait.homeworks.homework_13.service;

import de.ait.homeworks.homework_13.model.Product;
import de.ait.homeworks.homework_13.repository.OnlineStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlineShoreService {

    private final OnlineStoreRepository onlineStoreRepository;

    public OnlineShoreService(OnlineStoreRepository onlineStoreRepository) {
        this.onlineStoreRepository = onlineStoreRepository;

        this.onlineStoreRepository.saveAll(List.of(
                new Product(1L, "Banana", 1.5),
                new Product(2L, "Apple", 1.2),
                new Product(3L, "Orange", 1.0),
                new Product(4L, "Mango", 2.5),
                new Product(5L, "Grapes", 3.0),
                new Product(6L, "Pineapple", 2.0),
                new Product(7L, "Strawberry", 4.0),
                new Product(8L, "Watermelon", 5.0),
                new Product(9L, "Kiwi", 1.8),
                new Product(10L, "Peach", 2.2)
        ));
    }


    public Iterable<Product> getListOfProducts() {
        return onlineStoreRepository.findAll();
    }

    // todo  a "normal" logic
    public String getCustomerCart() {
        return "Banana: 1kg, Apple 2kg, Orange 1kg, Mango 2 st.";
    }

    // todo a "normal" logic
    public String addNewProductToStore(Long id, String name, double price) {
        Product productToAdd = new Product(id, name, price);

        return "The product added:" + productToAdd;
    }
}
