package com.example.minorproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class MinorProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinorProjectApplication.class, args);
	}

	/**
	 * TRACE - least severe // heart beat
	 * DEBUG - debugging
	 * INFO - when you want display only relevant info
	 * WARN - 2nd most severe
	 * ERROR - most severe
	 *
	 */

}
