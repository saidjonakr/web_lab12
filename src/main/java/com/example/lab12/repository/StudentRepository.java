package com.example.lab12.repository;

import com.example.lab12.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Relationship Queries
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT s FROM Student s WHERE SIZE(s.courses) > :minCourses")
    List<Student> findStudentsWithMoreThanXCourses(@Param("minCourses") int minCourses);

    // Custom Projection
    @Query("SELECT s.name as name, COUNT(c) as courseCount " +
            "FROM Student s LEFT JOIN s.courses c GROUP BY s.id")
    List<StudentCourseCountProjection> countCoursesPerStudent();

    // Named Query (defined in Student entity)
    List<Student> findByNamedQuery(String namePattern);

    public interface StudentCourseCountProjection {
        String getName();
        Long getCourseCount();
    }
}