package com.getir.onlinebooks.store.order.service;

import com.getir.onlinebooks.store.common.AppPage;
import com.getir.onlinebooks.store.order.entity.Order;
import com.getir.onlinebooks.store.order.model.OrderDTO;
import com.sun.istack.NotNull;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Order saveOrder(Order order, @NotNull List<OrderDTO.OrderItemDTO> orderItems);

    List<Order> findOrdersByCustomerId(Integer customerId, AppPage appPage);

    Order findOrderById(Integer orderId);

    List<Order> findOrderByDateInterval(Date fromDate, Date toDate);
}
