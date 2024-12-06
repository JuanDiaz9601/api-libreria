package com.alurachallenge.api_libreria;

import com.alurachallenge.api_libreria.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ApiLibreriaApplication implements CommandLineRunner {

    public static void main(String[] args) {
		SpringApplication.run(ApiLibreriaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.getDatosLibro();


	}

}
