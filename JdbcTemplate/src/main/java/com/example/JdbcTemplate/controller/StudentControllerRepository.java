package com.example.JdbcTemplate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.JdbcTemplate.Repository.StudentRepository;
import com.example.JdbcTemplate.model.Student;

@RestController
@RequestMapping("/api/v1/stu")
public class StudentControllerRepository {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentControllerRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get All students
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok().body(students);
    }

    // Get Student By id
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create New Student
    @PostMapping
    public ResponseEntity<Void> createStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return ResponseEntity.ok().build();
    }

    // Update Student
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setName(updatedStudent.getName());
            studentRepository.save(existingStudent);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Student By id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            studentRepository.delete(optionalStudent.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
