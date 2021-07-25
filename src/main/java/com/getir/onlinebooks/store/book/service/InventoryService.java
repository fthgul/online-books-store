package com.getir.onlinebooks.store.book.service;

import com.getir.onlinebooks.store.book.entity.Inventory;

public interface InventoryService {
    void decreaseAmount(Inventory inventory, int amount);

    Inventory findInventoryByBookId(Integer bookId);
}
