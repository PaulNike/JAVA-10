package com.codigo.restTemplate;

import com.codigo.restTemplate.exception.ConsultaReniecException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestTemplateApplication.class, args);
	}

	//public Throwable error(){
	//	throw new  ConsultaReniecException("ERORR");
	//}
}
