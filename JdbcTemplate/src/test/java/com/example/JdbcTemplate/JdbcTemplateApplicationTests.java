package com.example.JdbcTemplate;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.JdbcTemplate.model.Student;

@SpringBootTest
@AutoConfigureMockMvc
class JdbcTemplateApplicationTests {

	private static final String DEFAULT_NAME = "AAAAAAAAAA";
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Create entity for tests. Make sure to give different ids to each entity for
	 * primary key reasons.
	 */
	public Student createEntity(Long id) {
		Student student = new Student();
		student.setId(id);
		student.setName(DEFAULT_NAME);
		return student;
	}

	@Test
	public void createStudentTest() throws Exception {
		// create student
		Student student = createEntity(1L);
		mockMvc.perform(post("/api/v1/students").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(student))).andExpect(status().isOk());

		// Get the student and validate
		mockMvc.perform(get("/api/v1/students/{id}", student.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME));
	}

	@Test
	public void updateStudentTest() throws Exception {
		// create student
		Student student = createEntity(2L);
		mockMvc.perform(post("/api/v1/students").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(student))).andExpect(status().isOk());

		// Get the student and validate
		mockMvc.perform(get("/api/v1/students/{id}", student.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME));

		// update student
		student.setName(UPDATED_NAME);
		mockMvc.perform(put("/api/v1/students").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(student))).andExpect(status().isOk());

		// Get the student and validate update
		mockMvc.perform(get("/api/v1/students/{id}", student.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(UPDATED_NAME));
	}

	@Test
	public void deleteStudentTest() throws Exception {
		// create student
		Student student = createEntity(3L);
		mockMvc.perform(post("/api/v1/students").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(student))).andExpect(status().isOk());

		// delete student
		mockMvc.perform(delete("/api/v1/students/{id}", student.getId())).andExpect(status().isOk());

		// Get the student and validate delete
		mockMvc.perform(get("/api/v1/students/{id}", student.getId())).andExpect(status().isNoContent());
	}

	@Test
	public void getAllStudentTest() throws Exception {
		try {
			// create student
			Student student = createEntity(4L);
			mockMvc.perform(post("/api/v1/students").contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(student))).andExpect(status().isOk());
			Student student2 = createEntity(5L);
			mockMvc.perform(post("/api/v1/students").contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(student2))).andExpect(status().isOk());

			mockMvc.perform(get("/api/v1/students")).andExpect(status().isOk())
					.andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
					.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
		} catch (AssertionError e) {
			throw new AssertionError("Expecting the test to return a list with 2 students but the list is empty", e);
		}
	}
}