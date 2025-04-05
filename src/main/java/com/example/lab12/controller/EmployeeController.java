package com.example.lab12.controller;

import com.example.lab12.model.Employee;
import com.example.lab12.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository repository;

    // Category 1: CRUD Operations
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(employeeDetails.getName());
                    employee.setDepartment(employeeDetails.getDepartment());
                    employee.setSalary(employeeDetails.getSalary());
                    return ResponseEntity.ok(repository.save(employee));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    repository.delete(employee);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Category 2: Querying and Filtering
    @GetMapping("/filter/salary-above")
    public List<Employee> getEmployeesBySalary(@RequestParam Double threshold) {
        return repository.findBySalaryGreaterThan(threshold);
    }

    @GetMapping("/filter/department/{dept}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String dept) {
        return repository.findByDepartment(dept);
    }

    // Category 4: Advanced Pagination
    @GetMapping("/paginated")
    public Page<Employee> getPaginatedEmployees(
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
}