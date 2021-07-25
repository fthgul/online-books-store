package com.getir.onlinebooks.store.book.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UniqueBookVioaltionException extends DataIntegrityViolationException {
    public UniqueBookVioaltionException(String msg) {
        super(msg);
    }
}
