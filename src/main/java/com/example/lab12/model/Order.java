package com.example.lab12.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;

    @Field("customer_id")
    private String customerId;

    private LocalDate orderDate;
    private String status;
    private List<OrderItem> items;

    @Field("total_amount")
    private Double totalAmount;

    // Embedded document
    public static class OrderItem {
        private String productId;
        private Integer quantity;
        private Double price;

        // Getters and Setters
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
    }

    // Constructors, Getters and Setters
    public Order() {}

    public Order(String customerId, LocalDate orderDate, String status, List<OrderItem> items, Double totalAmount) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.items