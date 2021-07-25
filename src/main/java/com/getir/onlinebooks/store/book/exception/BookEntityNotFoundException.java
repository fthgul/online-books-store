package com.getir.onlinebooks.store.book.exception;

import javax.persistence.EntityNotFoundException;

public class BookEntityNotFoundException extends EntityNotFoundException {
    public BookEntityNotFoundException(String message) {
        super(message);
    }
}
