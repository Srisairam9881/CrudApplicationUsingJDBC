package com.example.JdbcTemplate.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="student")
@Getter
@Setter
public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1520645267798770010L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
