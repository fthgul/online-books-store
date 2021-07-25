package com.getir.onlinebooks.store.book.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BookDTO {
    private Integer id;
    @NotEmpty(message = "author cannot be empty")
    private String author;
    @NotEmpty(message = "name cannot be empty")
    private String name;
    private BigDecimal price;
    @NotNull(message = "inventory cannot be null")
    private InventoryDTO inventory;

    @Data
    public static class InventoryDTO {
        private int quantity;
    }
}
