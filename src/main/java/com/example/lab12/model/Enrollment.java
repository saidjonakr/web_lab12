package com.example.lab12.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
@IdClass(EnrollmentId.class)  // Composite key class
public class Enrollment {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate = LocalDate.now();

    @DecimalMin("0.0")
    @DecimalMax("4.0")
    private Double grade;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    // Constructors
    public Enrollment() {}

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    // Getters and Setters
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }
    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    // Enum for enrollment status
    public enum EnrollmentStatus {
        ACTIVE, COMPLETED, WITHDRAWN, FAILED
    }
}