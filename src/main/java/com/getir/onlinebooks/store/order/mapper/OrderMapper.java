package com.getir.onlinebooks.store.order.mapper;

import com.getir.onlinebooks.store.book.service.BookService;
import com.getir.onlinebooks.store.customer.entity.Customer;
import com.getir.onlinebooks.store.customer.service.CustomerService;
import com.getir.onlinebooks.store.order.entity.Order;
import com.getir.onlinebooks.store.order.entity.OrderItem;
import com.getir.onlinebooks.store.order.model.OrderDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class OrderMapper {

    private final BookService bookService;
    private final CustomerService customerService;

    public OrderMapper(BookService bookService, CustomerService customerService) {
        this.bookService = bookService;
        this.customerService = customerService;
    }

    public Order fromOrderDtoToOrderEntity(OrderDTO orderDTO) {
        Order order = new Order();
        Customer customer = customerService.findCustomerById(orderDTO.getCustomerId());
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setTotalAmount(new BigDecimal(0));
        return order;
    }

    public OrderDTO fromOrderEntityToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(order.getCustomer().getId());
        orderDTO.setId(order.getId());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setOrderDate(order.getOrderDate());

        final List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            OrderDTO.OrderItemDTO orderItemDTO = new OrderDTO.OrderItemDTO();
            orderItemDTO.setId(orderItem.getId());
            orderItemDTO.setBookId(orderItem.getBook().getId());
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setSobTotal(orderItem.getSubTotal());
            orderDTO.addOrderItemDTO(orderItemDTO);
        }
        return orderDTO;
    }
}
