package de.ait.homeworks.homework_13.repository;

import de.ait.homeworks.homework_13.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface OnlineStoreRepository extends CrudRepository<Product, Long> {
}
