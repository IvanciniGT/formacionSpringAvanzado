package com.fermin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.fermin") // Por ahí, en mi paquete hay definidos compoentes.. Los necesito en esta app
//@EntityScan("com.fermin")	 // Por ahí, en mi paquete hay entidades definidas... Las necesito en esta app.
public class FerminBackendApplication {

	public static void main(String[] args){
		SpringApplication.run(FerminBackendApplication.class, args); // Inversión de Control!
	}
	
}
