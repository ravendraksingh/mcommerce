package com.rks.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "orders")
@Table(name = "orders")
public class Order extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "order_date")
    private Date orderDate;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable= false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable=false, updatable = false)
    private Date updatedDate;

    public void addItem(String name, int quantity, BigDecimal price) {
        Item newItem = new Item();
        newItem.setName(name);
        newItem.setQuantity(quantity);
        newItem.setPrice(price);
        newItem.setOrder(this);
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(newItem);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                //", items=" + items +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
