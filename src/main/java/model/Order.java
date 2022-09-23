package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final LocalDateTime localDateTime;

    private final String orderId;

    private final List<Product> productList;

    public Order(String orderId, LocalDateTime localDateTime) {
        this.orderId = orderId;
        this.localDateTime = localDateTime;
        this.productList = new ArrayList<>();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        this.productList.add(product);
    }

}
