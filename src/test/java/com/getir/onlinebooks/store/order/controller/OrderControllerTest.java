package com.getir.onlinebooks.store.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.onlinebooks.store.order.entity.Order;
import com.getir.onlinebooks.store.order.mapper.OrderMapper;
import com.getir.onlinebooks.store.order.model.OrderDTO;
import com.getir.onlinebooks.store.order.service.OrderService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@EnableWebMvc
@SpringBootTest(classes = OrderController.class)
class OrderControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    OrderService orderService;

    @MockBean
    OrderMapper orderMapper;


    @SneakyThrows
    @Test
    void WhenIsOKSaveOrderTest() {
        File file = new File(this.getClass().getClassLoader().getResource("validOrder.json").getFile());
        final OrderDTO orderDTO = objectMapper.readValue(new FileInputStream(file), OrderDTO.class);

        mvc.perform(
                post("/order")
                        .content(objectMapper.writeValueAsString(orderDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    void WhenIsNotValidThenBadRequestSaveOrderTest() {
        File file = new File(this.getClass().getClassLoader().getResource("InvalidOrder.json").getFile());
        final OrderDTO orderDTO = objectMapper.readValue(new FileInputStream(file), OrderDTO.class);

        mvc.perform(
                post("/order")
                        .content(objectMapper.writeValueAsString(orderDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void WhenIsOkGetOrderByIdTest() {
        File file = new File(this.getClass().getClassLoader().getResource("validOrder.json").getFile());
        final OrderDTO orderDTO = objectMapper.readValue(new FileInputStream(file), OrderDTO.class);

        when(orderService.findOrderById(2)).thenReturn(new Order());
        mvc.perform(
                get("/order/2")
                        .content(objectMapper.writeValueAsString(orderDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @SneakyThrows
    @Test
    void whenIsOkGetOrderListByDateInterval() {
        File file = new File(this.getClass().getClassLoader().getResource("validOrder.json").getFile());
        final OrderDTO orderDTO = objectMapper.readValue(new FileInputStream(file), OrderDTO.class);

        when(orderService.findOrderById(2)).thenReturn(new Order());
        mvc.perform(
                get("/orders?fromDate=2021-07-22 00:00:00&toDate=2021-07-29 00:00:00")
                        .content(objectMapper.writeValueAsString(orderDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}