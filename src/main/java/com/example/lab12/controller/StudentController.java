package com.example.lab12.controller;

import com.example.lab12.model.Course;
import com.example.lab12.model.Student;
import com.example.lab12.repository.StudentRepository;
import com.example.lab12.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Basic CRUD
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Category 3: Relationships
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Student> enrollStudentInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        return studentRepository.findById(studentId)
                .flatMap(student -> courseRepository.findById(courseId)
                        .map(course -> {
                            student.addCourse(course);
                            return ResponseEntity.ok(studentRepository.save(student));
                        }))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<Set<Course>> getStudentCourses(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> ResponseEntity.ok(student.getCourses()))
                .orElse(ResponseEntity.notFound().build());
    }
}