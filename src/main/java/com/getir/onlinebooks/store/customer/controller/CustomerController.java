package com.getir.onlinebooks.store.customer.controller;

import com.getir.onlinebooks.store.common.model.AppPage;
import com.getir.onlinebooks.store.customer.entity.Customer;
import com.getir.onlinebooks.store.customer.service.CustomerService;
import com.getir.onlinebooks.store.order.entity.Order;
import com.getir.onlinebooks.store.order.mapper.OrderMapper;
import com.getir.onlinebooks.store.order.model.OrderDTO;
import com.getir.onlinebooks.store.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public CustomerController(CustomerService customerService, OrderService orderService, OrderMapper orderMapper) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("register")
    public Customer registerNewCustomer(@RequestBody @Valid Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping("{customerId}/orders")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Integer customerId, AppPage appPage) {
        List<Order> orders = orderService.findOrdersByCustomerId(customerId, appPage);
        final List<OrderDTO> orderDTOList =
                orders.stream().map(orderMapper::fromOrderEntityToOrderDTO).collect(Collectors.toList());
        return new ResponseEntity(orderDTOList, HttpStatus.OK);
    }
}
