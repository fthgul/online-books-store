package com.getir.onlinebooks.store.book.repository;

import com.getir.onlinebooks.store.book.entity.Book;
import com.getir.onlinebooks.store.book.entity.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InventoryRepositoryTest extends BaseRepositoryTest {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void WhenIsOkFindByBook_IdTest() {
        Book book = buildBookEntity();
        final Book persistedBook = entityManager.persist(book);
        Inventory fetchedInventory = inventoryRepository.findByBook_Id(persistedBook.getId()).get();
        assertThat(book.getInventory().getQuantity()).isEqualTo(fetchedInventory.getQuantity());
    }

    @Test
    void WhenIsNotFindByBook_IdTest() {
        Optional<Inventory> fetchedInventory = inventoryRepository.findByBook_Id(2);
        assertThat(fetchedInventory.isPresent()).isEqualTo(false);
    }
}