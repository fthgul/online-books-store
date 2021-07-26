package com.getir.onlinebooks.store.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.onlinebooks.store.book.entity.Book;
import com.getir.onlinebooks.store.book.entity.Inventory;
import com.getir.onlinebooks.store.book.exception.BookEntityNotFoundException;
import com.getir.onlinebooks.store.book.exception.InventoryEntityNotFoundException;
import com.getir.onlinebooks.store.book.repository.InventoryRepository;
import com.getir.onlinebooks.store.book.service.BookService;
import com.getir.onlinebooks.store.book.service.InventoryService;
import com.getir.onlinebooks.store.common.model.AppPage;
import com.getir.onlinebooks.store.order.entity.Order;
import com.getir.onlinebooks.store.order.entity.OrderItem;
import com.getir.onlinebooks.store.order.exception.OrderEntityNotFoundException;
import com.getir.onlinebooks.store.order.model.OrderDTO;
import com.getir.onlinebooks.store.order.repository.OrderRepository;
import com.getir.onlinebooks.store.order.service.impl.OrderServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrderServiceImpl.class)
class OrderServiceTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    BookService bookService;

    @MockBean
    InventoryService inventoryService;

    @MockBean
    InventoryRepository inventoryRepository;

    @SneakyThrows
    @Test
    void whenIsOkSaveOrderTest() {
        File file = new File(this.getClass().getClassLoader().getResource("validOrder.json").getFile());
        final OrderDTO orderDTO = objectMapper.readValue(new FileInputStream(file), OrderDTO.class);

        when(inventoryService.findInventoryByBookId(anyInt())).thenReturn(getInventory());
        when(bookService.findBookById(anyInt())).thenReturn(getBook());

        orderService.saveOrder(getOrder(), orderDTO.getOrderItems());
    }

    @SneakyThrows
    @Test
    void whenIsNotFoundBookIdSaveOrderTest() {
        File file = new File(this.getClass().getClassLoader().getResource("validOrder.json").getFile());
        final OrderDTO orderDTO = objectMapper.readValue(new FileInputStream(file), OrderDTO.class);

        when(inventoryService.findInventoryByBookId(anyInt())).thenReturn(getInventory());
        when(bookService.findBookById(anyInt())).thenThrow(BookEntityNotFoundException.class);

        assertThrows(
                BookEntityNotFoundException.class, () -> orderService.saveOrder(getOrder(), orderDTO.getOrderItems()));
    }

    @SneakyThrows
    @Test
    void whenIsNotFoundInventoryIdSaveOrderTest() {
        File file = new File(this.getClass().getClassLoader().getResource("validOrder.json").getFile());
        final OrderDTO orderDTO = objectMapper.readValue(new FileInputStream(file), OrderDTO.class);

        when(inventoryService.findInventoryByBookId(anyInt())).thenThrow(InventoryEntityNotFoundException.class);
        when(bookService.findBookById(anyInt())).thenReturn(getBook());

        assertThrows(
                InventoryEntityNotFoundException.class,
                () -> orderService.saveOrder(getOrder(), orderDTO.getOrderItems()));
    }

    @Test
    void whenIsOkFindOrdersByCustomerIdTest() {
        AppPage appPage = new AppPage();
        appPage.setPageNo(1);
        appPage.setPageSize(10);

        Order expectedOrder = getExpectedOrder();

        when(orderRepository.findByCustomer_Id(2, PageRequest.of(appPage.getPageNo(), appPage.getPageSize())))
                .thenReturn(Arrays.asList(expectedOrder));
        final List<Order> orders = orderService.findOrdersByCustomerId(2, appPage);

        assertThat(orders.size()).isEqualTo(expectedOrder.getOrderItems().size());
    }

    @Test
    void whenIsNotFoundFindOrdersByCustomerIdTest() {
        AppPage appPage = new AppPage();
        appPage.setPageNo(1);
        appPage.setPageSize(10);

        when(orderRepository.findByCustomer_Id(2, PageRequest.of(appPage.getPageNo(), appPage.getPageSize())))
                .thenThrow(OrderEntityNotFoundException.class);

        assertThrows(
                OrderEntityNotFoundException.class,
                () ->orderService.findOrdersByCustomerId(2, appPage));

    }

    @Test
    void whenIsOkFindOrderByIdTest() {
        final Order expectedOrder = getExpectedOrder();
        when(orderRepository.findById(1)).thenReturn(Optional.of(expectedOrder));
        final Order order = orderService.findOrderById(1);
        assertThat(order.getOrderDate()).isEqualTo(expectedOrder.getOrderDate());
    }

    @Test
    void whenIsNotFoundFindOrderByIdTest() {
        when(orderRepository.findById(1)).thenThrow(OrderEntityNotFoundException.class);
        assertThrows(
                OrderEntityNotFoundException.class,
                () ->orderService.findOrderById(1));

    }

    @Test
    void whenIsOkFindOrderByDateIntervalTest() {
        final Order expectedOrder = getExpectedOrder();
        when(orderRepository.findAllByOrderDateBetween(any(), any())).thenReturn(Arrays.asList(expectedOrder));
        final List<Order> orders = orderRepository.findAllByOrderDateBetween(new Date(), new Date());
        assertThat(orders.size()).isEqualTo(expectedOrder.getOrderItems().size());
    }

    @Test
    void whenIsEmptyFindOrderByDateIntervalTest() {
        when(orderRepository.findAllByOrderDateBetween(any(), any())).thenReturn(new ArrayList<>());
        final List<Order> orders = orderRepository.findAllByOrderDateBetween(new Date(), new Date());
        assertThat(orders.size()).isEqualTo(0);
    }


    private Book getBook() {
        Book book = new Book();
        book.setId(5);
        book.setPrice(new BigDecimal(10.20));
        return book;
    }

    private Order getOrder() {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setTotalAmount(new BigDecimal(0));
        return order;
    }

    private Inventory getInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(3);
        inventory.setBook(getBook());
        inventory.setQuantity(4);
        return inventory;
    }

    private Order getExpectedOrder() {
        Order order = getOrder();
        order.setTotalAmount(new BigDecimal(10.20));
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setQuantity(2);
        orderItem.setBook(getBook());
        orderItem.setSubTotal(new BigDecimal(10.20));
        order.setOrderItems(Arrays.asList(orderItem));
        return order;
    }
}
