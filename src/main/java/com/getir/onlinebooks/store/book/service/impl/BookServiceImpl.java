package com.getir.onlinebooks.store.book.service.impl;

import com.getir.onlinebooks.store.book.entity.Book;
import com.getir.onlinebooks.store.book.entity.Inventory;
import com.getir.onlinebooks.store.book.exception.BookEntityNotFoundException;
import com.getir.onlinebooks.store.book.exception.UniqueBookVioaltionException;
import com.getir.onlinebooks.store.book.model.BookDTO;
import com.getir.onlinebooks.store.book.repository.BookRepository;
import com.getir.onlinebooks.store.book.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.function.Supplier;


@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public Book updateBook(BookDTO bookDTO) {
        //check book id and inventory id
        if(bookDTO.getId() == null) {
            throw new ValidationException("book id can not be null");
        }

        final Book book = findBookById(bookDTO.getId());
        //update Book fields
        book.setPrice(bookDTO.getPrice());
        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());

        //update inventor
        final Inventory inventory = book.getInventory();
        inventory.setQuantity(bookDTO.getInventory().getQuantity());

        return bookRepository.save(book);
    }



    @Override
    @Transactional
    public Book saveBook(Book book) {
        final boolean isExistBook = bookRepository.existsByAuthorAndName(book.getAuthor(), book.getName());
        if(isExistBook) {
            throw new UniqueBookVioaltionException(book.getAuthor() + "'s  " + book.getName() + " book has already saved.");
        }
        return bookRepository.save(book);
    }

    @Override
    public Book findBookById(Integer bookId) {
        Supplier<BookEntityNotFoundException> s =
                () -> new BookEntityNotFoundException(bookId + " bookId: not found");

        return bookRepository.findBookById(bookId).orElseThrow(s);
    }
}
