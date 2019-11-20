package com.rks.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.persistence.*;
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
    @Column(name = "order_id")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "order_date")
    private Date orderDate;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @JsonIgnore
    @Column(name = "order_status")
    private String orderStatus;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonIgnore
    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable= false, updatable = false)
    private Date createdDate;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonIgnore
    @Column(name = "updated_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable=false, updatable = false)
    private Date updatedDate;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", items=" + items +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
