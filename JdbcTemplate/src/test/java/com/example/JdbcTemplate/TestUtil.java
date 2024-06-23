package com.example.JdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestUtil {
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
}
