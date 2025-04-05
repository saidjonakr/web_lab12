package com.example.lab12.controller;

import com.example.lab12.model.Product;
import com.example.lab12.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    // CRUD Operations
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    // Category 4: Advanced Pagination/Sorting
    @GetMapping("/paginated")
    public Page<Product> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return repository.findAll(
                PageRequest.of(page, size, Sort.by(sortDirection, sortBy))
        );
    }

    // Category 4: Filtered Pagination
    @GetMapping("/filtered")
    public Page<Product> getFilteredProducts(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return repository.findByPriceBetween(
                minPrice,
                maxPrice,
                PageRequest.of(page, size, Sort.by("price"))
        );
    }

    // Category 2: Querying
    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String keyword,
            @RequestParam(required = false) BigDecimal maxPrice) {

        if (maxPrice != null) {
            return repository.findByDescriptionContainingAndPriceLessThanEqual(keyword, maxPrice);
        }
        return repository.findByDescriptionContaining(keyword);
    }
}