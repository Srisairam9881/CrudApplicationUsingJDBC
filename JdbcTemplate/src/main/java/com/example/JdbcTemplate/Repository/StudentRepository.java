package com.example.JdbcTemplate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JdbcTemplate.model.Student;

public interface StudentRepository extends JpaRepository<Student,Long>{
}
