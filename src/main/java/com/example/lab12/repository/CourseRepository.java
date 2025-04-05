package com.example.lab12.repository;

import com.example.lab12.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Relationship Queries
    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);

    // Derived Query
    List<Course> findByTitleContainingIgnoreCase(String title);

    // Complex Join
    @Query("SELECT c FROM Course c WHERE " +
            "(SELECT COUNT(s) FROM Student s WHERE s MEMBER OF c.students) > :minStudents")
    List<Course> findPopularCourses(@Param("minStudents") int minStudents);

    // Update Query
    @Query("UPDATE Course c SET c.code = :newCode WHERE c.id = :courseId")
    void updateCourseCode(@Param("courseId") Long courseId, @Param("newCode") String newCode);
}