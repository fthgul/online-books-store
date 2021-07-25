package com.getir.onlinebooks.store.order.controller;

import com.getir.onlinebooks.store.order.entity.Order;
import com.getir.onlinebooks.store.order.mapper.OrderMapper;
import com.getir.onlinebooks.store.order.model.OrderDTO;
import com.getir.onlinebooks.store.order.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("order")
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody @Valid OrderDTO orderDto) {
        final Order order = orderService.saveOrder(orderMapper.fromOrderDtoToOrderEntity(orderDto), orderDto.getOrderItems());
        return new ResponseEntity(orderMapper.fromOrderEntityToOrderDTO(order), HttpStatus.CREATED);
    }

    @GetMapping("order/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) {
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity(orderMapper.fromOrderEntityToOrderDTO(order), HttpStatus.OK);
    }

    @GetMapping("orders")
    public ResponseEntity<List<Order>> getOrderListByDateInterval(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate) {
        List<Order> orders = orderService.findOrderByDateInterval(fromDate, toDate);
        final List<OrderDTO> orderDTOList =
                orders.stream().map(orderMapper::fromOrderEntityToOrderDTO).collect(Collectors.toList());
        return new ResponseEntity(orderDTOList, HttpStatus.OK);
    }
}
