package com.getir.onlinebooks.store.book.exception;

import javax.persistence.EntityNotFoundException;

public class InventoryEntityNotFoundException extends EntityNotFoundException {
    public InventoryEntityNotFoundException(String message) {
        super(message);
    }
}
