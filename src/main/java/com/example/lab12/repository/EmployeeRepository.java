package com.example.lab12.repository;

import com.example.lab12.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Category 2: Querying Methods
    List<Employee> findByDepartment(String department);

    List<Employee> findBySalaryGreaterThan(Double salary);

    List<Employee> findBySalaryBetween(Double minSalary, Double maxSalary);

    // Custom Query with JPQL
    @Query("SELECT e FROM Employee e WHERE e.department = :dept AND e.salary > :minSalary")
    List<Employee> findEmployeesByDeptAndMinSalary(
            @Param("dept") String department,
            @Param("minSalary") Double minSalary);

    // Category 4: Pagination
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByDepartment(String department, Pageable pageable);

    // Native Query Example
    @Query(value = "SELECT * FROM employee WHERE salary > :threshold ORDER BY name",
            nativeQuery = true)
    List<Employee> findEmployeesEarningAboveNative(@Param("threshold") Double salaryThreshold);
}