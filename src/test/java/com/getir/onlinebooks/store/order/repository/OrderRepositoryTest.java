package com.getir.onlinebooks.store.order.repository;

import com.getir.onlinebooks.store.book.entity.Book;
import com.getir.onlinebooks.store.book.repository.BaseRepositoryTest;
import com.getir.onlinebooks.store.customer.entity.Customer;
import com.getir.onlinebooks.store.order.entity.Order;
import com.getir.onlinebooks.store.order.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest extends BaseRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void findByCustomer_IdTest() {
        //persist customer
        Customer customer = getCustomer();
        entityManager.persist(customer);

        //persist book
        Book book = buildBookEntity();
        entityManager.persist(book);

        Order order = getExpectedOrder();
        order.setCustomer(customer);
        order.getOrderItems().get(0).setBook(book);

        //persist order
        final Order persistedOrder = entityManager.persist(order);

        final List<Order> orders = orderRepository.findByCustomer_Id(order.getId(), PageRequest.of(0, 10));
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getCustomer().getId()).isEqualTo(persistedOrder.getCustomer().getId());
    }


    private Order getExpectedOrder() {
        Order order = getOrder();
        order.setTotalAmount(new BigDecimal(10.10));
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(2);
        orderItem.setSubTotal(new BigDecimal(10.20));
        order.setOrderItems(Arrays.asList(orderItem));
        return order;
    }

    private Order getOrder() {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setTotalAmount(new BigDecimal(10.10));
        return order;
    }


    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setEmail("email@gmail.com");
        customer.setUsername("test");
        return customer;
    }
}