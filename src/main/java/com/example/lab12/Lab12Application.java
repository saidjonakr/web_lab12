package com.example.lab12;

import com.example.lab12.model.Course;
import com.example.lab12.model.Employee;
import com.example.lab12.model.Product;
import com.example.lab12.model.Student;
import com.example.lab12.repository.CourseRepository;
import com.example.lab12.repository.EmployeeRepository;
import com.example.lab12.repository.ProductRepository;
import com.example.lab12.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class Lab12Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab12Application.class, args);
    }

    @Bean
    public CommandLineRunner demoData(
            EmployeeRepository employeeRepo,
            StudentRepository studentRepo,
            CourseRepository courseRepo,
            ProductRepository productRepo) {
        return args -> {
            // Category 1: CRUD Sample Data
            employeeRepo.saveAll(Arrays.asList(
                    new Employee("John Doe", "IT", 75000.0),
                    new Employee("Jane Smith", "HR", 65000.0),
                    new Employee("Bob Johnson", "Finance", 85000.0)
            ));

            // Category 3: Relationships Sample Data
            Student s1 = studentRepo.save(new Student("Alice Cooper"));
            Student s2 = studentRepo.save(new Student("Bob Marley"));

            Course c1 = courseRepo.save(new Course("Computer Science", "CS101"));
            Course c2 = courseRepo.save(new Course("Mathematics", "MATH202"));

            s1.addCourse(c1);
            s1.addCourse(c2);
            s2.addCourse(c1);

            studentRepo.saveAll(Arrays.asList(s1, s2));

            // Category 4: Advanced Topics Sample Data
            productRepo.saveAll(Arrays.asList(
                    new Product("Laptop", "High-performance laptop", BigDecimal.valueOf(999.99), 10),
                    new Product("Smartphone", "Latest model", BigDecimal.valueOf(699.99), 25),
                    new Product("Tablet", "Mid-range tablet", BigDecimal.valueOf(299.99), 15),
                    new Product("Monitor", "4K display", BigDecimal.valueOf(399.99), 8),
                    new Product("Keyboard", "Mechanical", BigDecimal.valueOf(89.99), 30)
            ));

            System.out.println("Sample data loaded successfully!");
        };
    }
}