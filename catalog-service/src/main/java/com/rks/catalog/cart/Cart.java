package com.rks.catalog.cart;

import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {

    private int itemCount;
    Map<String, Integer> cartItems;

    public Cart() {
        itemCount = 0;
        cartItems = new HashMap<>();
    }

    public void addItem(String sku, int quantity) {
        if (!cartItems.containsKey(sku)) {
            cartItems.put(sku, quantity);
        } else {
            cartItems.put(sku, cartItems.get(sku)+quantity);
        }
        itemCount += quantity;
    }

    public Map<String, Integer> getCartItems() {
        return cartItems;
    }

    public int getQuantity(String sku) {
        return cartItems.get(sku);
    }

    public int getItemSize() {
        return itemCount;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "itemCount=" + itemCount +
                ", cartItems=" + cartItems +
                '}';
    }
}
