package com.example.JdbcTemplate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.JdbcTemplate.model.Student;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
	private  NamedParameterJdbcTemplate jdbcTemplate;
	 @Autowired
	 public StudentController(NamedParameterJdbcTemplate jdbcTemplate) {
	    this.jdbcTemplate = jdbcTemplate;
	 }
	
	 //Get All students
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getStudents(){
		List<Student> students=jdbcTemplate.query("Select * from student", new StudentRowMapper());
		return ResponseEntity.ok().body(students);
	}
	
	//Get Student By id
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable Long id){
			try {
				String sql = "SELECT * FROM student WHERE id = :id";
		        Student student = jdbcTemplate.queryForObject(
		            sql,
		            new MapSqlParameterSource().addValue("id", id),
		            new StudentRowMapper()
		        );
			if(student==null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok().body(student);
			}catch (EmptyResultDataAccessException e) {
				return ResponseEntity.noContent().build();
			}
	}
	
	//Create New Student
	@PostMapping("/students")
	public ResponseEntity<Void> createStudent(@RequestBody Student student){
		String sql="INSERT INTO student (id,name) VALUES (:id, :name)";
	    jdbcTemplate.update(sql,new MapSqlParameterSource()
	    		                    .addValue("id", student.getId())
	    		                    .addValue("name", student.getName()));
		return ResponseEntity.ok().build();
	}
	
	//Update Student
	@PutMapping("/students")
	public ResponseEntity<Void> updateStudent(@RequestBody Student student){
		String sql="UPDATE student SET name= :name where id= :id";
	    jdbcTemplate.update(sql,new MapSqlParameterSource()
	    		                    .addValue("id", student.getId())
	    		                    .addValue("name", student.getName()));
		return ResponseEntity.ok().build();
	}

	//Delete Student By id
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Void> DeleteStudent(@PathVariable Long id){
		jdbcTemplate.update("Delete from student where id= :id", new MapSqlParameterSource().addValue("id", id));
		return ResponseEntity.ok().build();
	}
}
