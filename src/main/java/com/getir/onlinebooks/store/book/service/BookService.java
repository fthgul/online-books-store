package com.getir.onlinebooks.store.book.service;

import com.getir.onlinebooks.store.book.entity.Book;
import com.getir.onlinebooks.store.book.model.BookDTO;

public interface BookService {
    Book updateBook(BookDTO bookDTO);
    Book findBookById(Integer bookId);
    Book saveBook(Book fromBookDtoToBookEntity);
}
