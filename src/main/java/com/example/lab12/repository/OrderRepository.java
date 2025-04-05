package com.example.lab12.repository;

import com.example.lab12.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    // MongoDB JSON Query Methods
    List<Order> findByStatus(String status);

    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("{'status': ?0, 'totalAmount': {$gt: ?1}}")
    List<Order> findHighValueOrdersByStatus(String status, Double minAmount);

    @Query(value = "{'customer.id': ?0}", fields = "{'items': 1, 'orderDate': 1}")
    List<Order> findCustomerOrdersWithProjection(String customerId);

    // Category 4: Aggregation
    @Query(value = "{}", fields = "{'status': 1, 'count': {$sum: 1}}")
    List<OrderStatusCount> countOrdersByStatus();

    interface OrderStatusCount {
        String getStatus();
        int getCount();
    }
}