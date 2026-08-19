package com.truww.googleapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoogleapiApplication implements CommandLineRunner {

	private final GoogleSheetsService googleSheetsService;

	public GoogleapiApplication(
			GoogleSheetsService googleSheetsService) {
		this.googleSheetsService = googleSheetsService;
	}

	public static void main(String[] args) {
		SpringApplication.run(
				GoogleapiApplication.class,
				args
		);
	}

	@Override
	public void run(String... args) {

		try {
			googleSheetsService.readSheet();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
