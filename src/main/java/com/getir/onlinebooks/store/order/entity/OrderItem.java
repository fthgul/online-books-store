package com.getir.onlinebooks.store.order.entity;

import com.getir.onlinebooks.store.book.entity.Book;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private Integer quantity;

    @Column(name = "sub_total")
    private BigDecimal subTotal;

}
