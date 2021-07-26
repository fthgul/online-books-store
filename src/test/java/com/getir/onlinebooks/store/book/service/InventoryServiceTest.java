package com.getir.onlinebooks.store.book.service;

import com.getir.onlinebooks.store.book.entity.Inventory;
import com.getir.onlinebooks.store.book.exception.InventoryEntityNotFoundException;
import com.getir.onlinebooks.store.book.repository.InventoryRepository;
import com.getir.onlinebooks.store.book.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InventoryServiceImpl.class)
class InventoryServiceTest {

    @Autowired
    InventoryService inventoryService;

    @MockBean
    InventoryRepository inventoryRepository;

    @Test
    void whenIsOkDecreaseAmountTest() {
        Inventory inventory = new Inventory();
        inventory.setQuantity(5);
        int initQuantity = inventory.getQuantity();

        when(inventoryRepository.save(inventory)).thenReturn(inventory);

        inventoryService.decreaseAmount(inventory, 3);
        assertThat(inventory.getQuantity()).isEqualTo(initQuantity - 3);
    }

    @Test
    void whenIsZeroBookInventoryTest() {
        Inventory inventory = new Inventory();
        inventory.setQuantity(0);

        when(inventoryRepository.save(inventory)).thenReturn(inventory);
        assertThrows(ValidationException.class, () -> inventoryService.decreaseAmount(inventory, 3));

    }

    @Test
    void whenIsBiggerThanBookInventoryTest() {
        Inventory inventory = new Inventory();
        inventory.setQuantity(2);

        when(inventoryRepository.save(inventory)).thenReturn(inventory);
        assertThrows(ValidationException.class, () -> inventoryService.decreaseAmount(inventory, 3));

    }

    @Test
    void WhenIsOkFindInventoryByBookIdTest() {
        when(inventoryRepository.findByBook_Id(2)).thenReturn(java.util.Optional.of(new Inventory()));
        inventoryService.findInventoryByBookId(2);
    }

    @Test
    void WhenIsNotFindInventoryByBookIdTest() {
        when(inventoryRepository.findByBook_Id(2)).thenThrow(InventoryEntityNotFoundException.class);
        assertThrows(InventoryEntityNotFoundException.class, () ->  inventoryService.findInventoryByBookId(2));
    }
}