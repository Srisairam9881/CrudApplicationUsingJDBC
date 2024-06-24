package com.example.JdbcTemplate.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.JdbcTemplate.model.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/student")
public class StudentControllerEntityManager {

    @PersistenceContext
    private EntityManager entityManager;

    // Get All students
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = entityManager.createQuery("SELECT s FROM Student s", Student.class)
                                               .getResultList();
        return ResponseEntity.ok().body(students);
    }

    // Get Student By id
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(student);
    }

    // Create New Student
    @PostMapping
    @Transactional
    public ResponseEntity<Void> createStudent(@RequestBody Student student) {
        entityManager.persist(student);
        return ResponseEntity.ok().build();
    }

    // Update Student
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student existingStudent = entityManager.find(Student.class, id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }
        existingStudent.setName(student.getName());
        entityManager.merge(existingStudent);
        return ResponseEntity.ok().build();
    }

    // Delete Student By id
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        entityManager.remove(student);
        return ResponseEntity.ok().build();
    }
}
