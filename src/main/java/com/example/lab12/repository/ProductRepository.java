package com.example.lab12.repository;

import com.example.lab12.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Category 2: Filtering Methods
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByDescriptionContaining(String keyword);

    List<Product> findByDescriptionContainingAndPriceLessThanEqual(String keyword, BigDecimal maxPrice);

    // Category 4: Advanced Pagination
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    // Custom Query with Sorting
    @Query("SELECT p FROM Product p WHERE p.stock > 0 ORDER BY p.price DESC")
    List<Product> findAvailableProductsSortedByPriceDesc();

    // Aggregation Query
    @Query("SELECT AVG(p.price) FROM Product p WHERE p.stock > 0")
    Double findAveragePriceOfAvailableProducts();
}