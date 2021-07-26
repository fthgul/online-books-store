package com.getir.onlinebooks.store.book.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.onlinebooks.store.book.entity.Book;
import com.getir.onlinebooks.store.book.entity.Inventory;
import com.getir.onlinebooks.store.book.exception.BookEntityNotFoundException;
import com.getir.onlinebooks.store.book.exception.UniqueBookVioaltionException;
import com.getir.onlinebooks.store.book.model.BookDTO;
import com.getir.onlinebooks.store.book.repository.BookRepository;
import com.getir.onlinebooks.store.book.service.impl.BookServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ValidationException;
import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @SneakyThrows
    @Test
    void whenValidBookUpdateTest() {
        File file = new File(this.getClass().getClassLoader().getResource("validBook.json").getFile());
        final BookDTO bookDTO = objectMapper.readValue(new FileInputStream(file), BookDTO.class);
        bookDTO.setId(1);

        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setInventory(new Inventory());
        when(bookRepository.findBookById(bookDTO.getId())).thenReturn(java.util.Optional.of(expectedBook));
        bookService.updateBook(bookDTO);
    }

    @SneakyThrows
    @Test
    void whenWithoutBookIdUpdateBookTest() {
        File file = new File(this.getClass().getClassLoader().getResource("validBook.json").getFile());
        final BookDTO bookDTO = objectMapper.readValue(new FileInputStream(file), BookDTO.class);

        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setInventory(new Inventory());
        when(bookRepository.findBookById(bookDTO.getId())).thenReturn(java.util.Optional.of(expectedBook));
        assertThrows(ValidationException.class, () -> bookService.updateBook(bookDTO));
    }

    @Test
    void whenNotFoundEntityFindBookByIdTest() {
        when(bookRepository.findBookById(1)).thenThrow(BookEntityNotFoundException.class);
        assertThrows(BookEntityNotFoundException.class, () -> bookService.findBookById(1));
    }

    @Test
    void whenFindBookByIdIsOkTest() {
        when(bookRepository.findBookById(1)).thenReturn(java.util.Optional.of(new Book()));
        bookService.findBookById(1);
    }

    @Test
    void whenNewSaveBookIsOkTest() {
        when(bookRepository.existsByAuthorAndName(anyString(), anyString())).thenReturn(false);

        // only set author and name
        Book book = new Book();
        book.setAuthor("author");
        book.setName("bookName");

        bookService.saveBook(new Book());
    }

    @Test
    void whenAlreadySavedBookTest() {
        when(bookRepository.existsByAuthorAndName(anyString(), anyString())).thenReturn(true);

        // only set author and name
        Book book = new Book();
        book.setAuthor("author");
        book.setName("bookName");

        assertThrows(UniqueBookVioaltionException.class, () -> bookService.saveBook(book));
    }
}
