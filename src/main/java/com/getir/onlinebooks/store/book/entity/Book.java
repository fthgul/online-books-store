package com.getir.onlinebooks.store.book.entity;

import com.getir.onlinebooks.store.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Audited
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Inventory inventory;

    private String name;
    private String author;
    private BigDecimal price;

    public Book() {
    }

    public void setInventory(Inventory inventory) {
        if (inventory == null) {
            if (this.inventory != null) {
                this.inventory.setBook(null);
            }
        }
        else {
            inventory.setBook(this);
        }
        this.inventory = inventory;
    }
}
