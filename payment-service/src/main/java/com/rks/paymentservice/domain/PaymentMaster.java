package com.rks.paymentservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment_master")
public class PaymentMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @JsonProperty("order_id")
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "order_date")
    private Date orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "payment_date")
    private Date paymentDate;

    @JsonProperty("payment_status")
    @Column(name = "payment_status")
    private String paymentStatus;

    @JsonIgnore
    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable= false, updatable = false)
    private Date createdDate;

    @JsonIgnore
    @Column(name = "updated_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable=false, updatable = false)
    private Date updatedDate;

    @Override
    public String toString() {
        return "PaymentMaster{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", paymentDate=" + paymentDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
