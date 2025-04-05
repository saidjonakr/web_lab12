package com.example.lab12.model;

import java.io.Serializable;
import java.util.Objects;

public class EnrollmentId implements Serializable {
    private Long student;
    private Long course;

    // Constructors
    public EnrollmentId() {}

    public EnrollmentId(Long studentId, Long courseId) {
        this.student = studentId;
        this.course = courseId;
    }

    // Getters and Setters
    public Long getStudent() { return student; }
    public void setStudent(Long student) { this.student = student; }
    public Long getCourse() { return course; }
    public void setCourse(Long course) { this.course = course; }

    // Important: Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return student.equals(that.student) && course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }
}