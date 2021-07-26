package com.getir.onlinebooks.store.book.repository;

import com.getir.onlinebooks.store.book.entity.Book;
import com.getir.onlinebooks.store.book.entity.Inventory;

import java.math.BigDecimal;
import java.util.Date;

public abstract class BaseRepositoryTest {

    protected Book buildBookEntity() {
        Book book = new Book();
        book.setName("test");
        book.setAuthor("test author");
        book.setPrice(new BigDecimal(10.20));
        book.setCreatedBy("testuser");
        book.setLastModifiedBy("testUser");
        book.setCreatedDate(new Date());
        book.setLastModifiedDate(new Date());
        Inventory inventory = new Inventory();
        inventory.setQuantity(3);
        book.setInventory(inventory);
        return book;
    }
}
