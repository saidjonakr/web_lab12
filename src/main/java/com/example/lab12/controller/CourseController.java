package com.example.lab12.controller;

import com.example.lab12.model.Course;
import com.example.lab12.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseRepository repository;

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return repository.save(course);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getCourseStudents(@PathVariable Long id) {
        return repository.findById(id)
                .map(course -> ResponseEntity.ok(course.getStudents()))
                .orElse(ResponseEntity.notFound().build());
    }
}