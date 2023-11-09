package com.fermin.animalitos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fermin.TestApplication;
import com.fermin.animalitos.repository.AnimalitosRepository;
import com.fermin.emails.service.EmailsService;

@ExtendWith(SpringExtension.class)					// Que JUnit PUEDA solicitar datos a Spring
@SpringBootTest(classes = TestApplication.class)	// Arranca una app que he de tener configurada (De antemano) para que incluya mi Servicio
public class AnimalitosServiceImplUnitTest {

	private final AnimalitosService animalitosService;
	
	@MockBean
	private AnimalitosRepository animalitosRepository;
	// Se genera a través de Mockito un Mock (Realmente un test-double:dummy)
	// Cuidado... en este caso, cuantas implementaciones habría del repo de Animalitos? 2
	//  - La real que me genera Spring
	//  - La de mentirijilla que me genera Mockito
	// Esa anotación NO ES DE MOCKITO, sino de SPRING.
	// Esa anotación, además de solicitar a Mockito que cree el dummy, fuerza a que se use esa implementación en lugar de cualquier otra que hubiera configurada
	
	@MockBean
	private EmailsService emailsService;
	
	
	AnimalitosServiceImplUnitTest(@Autowired AnimalitosService animalitosService){
		this.animalitosService=animalitosService;
	}
	
	@Test
	@DisplayName("Alta de un animalito con datos guays")
	void altaDeAnimalitoGuay() {
		// Dado				Un servicio de Animalitos configurado √
		// Y 				Un servicio de Emails de mentirijilla, que no envia correos, pero registra las peticiones de envío 			√ DUMMY
		// Y 				Un repositorio de Animalitos de mentirijilla, que siempre devuelve los mismos datos que recibe con id 33 	√ DUMMY
		//Dado				unos datos de un nuevo animalito
		//Y 				ese nuevo animalito tiene por "nombre": "<nombre>"
		//Y 				ese nuevo animalito tiene por "tipo": "<tipo>"
		//Y 				ese nuevo animalito tiene por "edad": <edad>
		//Cuando			se solicita al servicio de animalitos el alta de ese nuevo animalito
		//Entonces			se devuelve un os datos de animalito
		//Y 				los datos del animalito tienen por "id": 33
		//Y 				los datos del animalito tienen por "nombre": "<nombre>"
		//Y 				los datos del animalito tienen por "tipo": "<tipo>"
		//Y 				los datos del animalito tienen por "edad": <edad>
		//Y         		y se debe haber solicitado el envío de un email al usuario "altas@animalitosfermin.com"
		//Y         		y el correo solicitado debe tener por asunto: "Nuevo animalito"
		//Y         		y el cuerpo del correo solicitado debe contener: "Se ha dado de alta al animalito: <nombre>"
	}
	
}
