package com.getir.onlinebooks.store.order.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Integer id;
    @NotNull(message = "customerId cannot be null")
    private Integer customerId;
    private BigDecimal totalAmount;
    private Date orderDate;
    @NotNull(message = "orderItem cannot be null")
    private List<OrderItemDTO> orderItems;

    @Data
    public static class OrderItemDTO {
        private Integer id;
        @NotNull(message = "bookId cannot be null")
        private Integer bookId;
        private int quantity;
        private BigDecimal sobTotal;
    }

    public void addOrderItemDTO(OrderItemDTO orderItemDTO) {
        if(orderItems == null) {
            orderItems = new ArrayList<>();
        }
        orderItems.add(orderItemDTO);
    }
}
