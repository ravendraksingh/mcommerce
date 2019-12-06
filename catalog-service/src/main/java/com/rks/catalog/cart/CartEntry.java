package com.rks.catalog.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartEntry {
    private String imageUrl;
    private String sku;
    private double price;
    private int quantity;
    private double productTotalPrice;
}
