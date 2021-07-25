package com.getir.onlinebooks.store.book.controller;

import com.getir.onlinebooks.store.book.entity.Book;
import com.getir.onlinebooks.store.book.mapper.BookMapper;
import com.getir.onlinebooks.store.book.model.BookDTO;
import com.getir.onlinebooks.store.book.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("book")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public BookDTO saveBook(@RequestBody @Valid  BookDTO bookDTO) {
        final Book book = bookService.saveBook(bookMapper.fromBookDtoToBookEntity(bookDTO));
        return bookMapper.fromBookEntityToBookDTO(book);
    }

    @PutMapping
    public BookDTO updateBookInventory(@RequestBody @Valid BookDTO bookDTO) {
        final Book book = bookService.updateBook(bookDTO);
        return bookMapper.fromBookEntityToBookDTO(book);
    }
}
