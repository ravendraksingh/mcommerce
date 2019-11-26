package com.rks.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rks.orderservice.domain.BaseEntity;
import com.rks.orderservice.domain.Item;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderRequest {

    @JsonIgnore
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date orderDate;

    private List<Item> items = new ArrayList<>();

    @JsonIgnore
    private String orderStatus;

}
