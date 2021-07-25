package com.getir.onlinebooks.store.customer.entity;

import com.getir.onlinebooks.store.order.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "username cannot be empty")
    private String username;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
    @Email(message = "malformed email")
    @NotEmpty(message = "email cannot be empty")
    private String email;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
