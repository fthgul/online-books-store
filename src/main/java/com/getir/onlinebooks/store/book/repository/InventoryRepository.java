package com.getir.onlinebooks.store.book.repository;

import com.getir.onlinebooks.store.book.entity.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Integer> {
    Optional<Inventory> findByBook_Id(Integer bookId);
}
