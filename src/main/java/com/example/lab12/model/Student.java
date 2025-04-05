package com.example.lab12.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import java.util.*;
@OneToMany(mappedBy = "student")
private Set<Enrollment> enrollments = new HashSet<>();
public void enrollInCourse(Course course) {
    Enrollment enrollment = new Enrollment(this, course);
    enrollments.add(enrollment);
    course.getEnrollments().add(enrollment);
}
public void unenrollFromCourse(Course course) {
    Enrollment enrollment = new Enrollment(this, course);
    enrollments.remove(enrollment);
    course.getEnrollments().remove(enrollment);
    enrollment.setStudent(null);
    enrollment.setCourse(null);
}
@Entity
@Table(name = "students")
@NamedQueries({
        @NamedQuery(
                name = "Student.findByNamedQuery",
                query = "SELECT s FROM Student s WHERE s.name LIKE :namePattern"
        )
})

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NotBlank
    @Column(nullable = false, unique = true)
    private String studentId;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Course> courses = new HashSet<>();

    // Constructors
    public Student() {}

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    // Helper methods for relationship management
    public void addCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Course> getCourses() { return courses; }
    public void setCourses(Set<Course> courses) { this.courses = courses; }
}