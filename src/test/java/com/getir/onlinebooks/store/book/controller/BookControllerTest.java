package com.getir.onlinebooks.store.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.onlinebooks.store.book.mapper.BookMapper;
import com.getir.onlinebooks.store.book.model.BookDTO;
import com.getir.onlinebooks.store.book.service.BookService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@EnableWebMvc
@SpringBootTest(classes = BookController.class)
class BookControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookMapper bookMapper;

    @SneakyThrows
    @Test
    void testSaveValidBookIsOk() {
        File file = new File(this.getClass().getClassLoader().getResource("validBook.json").getFile());
        final BookDTO bookDTO = objectMapper.readValue(new FileInputStream(file), BookDTO.class);

        mvc.perform(
                        post("/book")
                                .content(asJsonString(bookDTO))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void testSaveInValidBookBadRequest() {
        File file = new File(this.getClass().getClassLoader().getResource("InvalidBook.json").getFile());
        final BookDTO bookDTO = objectMapper.readValue(new FileInputStream(file), BookDTO.class);

        mvc.perform(
                        post("/book")
                                .content(asJsonString(bookDTO))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void testUpdateBookInventory() {
        File file = new File(this.getClass().getClassLoader().getResource("validBook.json").getFile());
        final BookDTO bookDTO = objectMapper.readValue(new FileInputStream(file), BookDTO.class);

        mvc.perform(
                        put("/book")
                                .content(asJsonString(bookDTO))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String asJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
