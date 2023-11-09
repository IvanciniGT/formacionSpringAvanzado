package com.fermin.animalitos.controller.rest.v1;

import org.json.JSONObject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fermin.TestApplication;
import com.fermin.animalitos.entity.TipoAnimalito;
import com.fermin.emails.service.EmailsService;

@ExtendWith(SpringExtension.class)					// Que JUnit PUEDA solicitar datos a Spring
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)	// Arranca una app que he de tener configurada (De antemano) para que incluya mi Servicio
//@TestInstance(Lifecycle.PER_CLASS) Con esta anotación, JUnit crea una úinica instancia de la clase en la que ejecuta todas las funciones de test
// Esto es lo que necesito si quiero meter un Hook de JUNIT (Before... After...) que trabaje con datos a nivel de instancia
@AutoConfigureMockMvc // Crea un cliente HTTP de pruebas para mi app
public class AnimalitosRestControllerV1End2EndTest {

	private MockMvc clienteWeb;
	@MockBean
	private EmailsService emailsService;
	AnimalitosRestControllerV1End2EndTest(@Autowired MockMvc clienteWeb){
		this.clienteWeb=clienteWeb;
	}

	@Test
	@DisplayName("petición http post, mandando un objeto JSON")
	void altaGuay() throws Exception {
		String nombre = "firulais";
		int edad = 1;
		TipoAnimalito tipo = TipoAnimalito.PERRO;
		JSONObject objetoJson = new JSONObject();
		objetoJson.put("nombre", nombre);
		objetoJson.put("edad", edad);
		objetoJson.put("tipo", tipo);
		
		ResultActions resultado = clienteWeb.perform(MockMvcRequestBuilders.post("/api/v1/animalitos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objetoJson.toString()));
		resultado.andExpect(status().isCreated());
		resultado.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		resultado.andExpect(jsonPath("$.nombre").value(nombre));
		resultado.andExpect(jsonPath("$.tipo").value(tipo.toString()));
		resultado.andExpect(jsonPath("$.edad").value(edad));
		resultado.andExpect(jsonPath("$.id").isNumber());
	}

	@Test
	@DisplayName("petición http post, mandando un objeto JSON inválido. Sin nombre")
	void altaRuina1() throws Exception {
		int edad = 1;
		TipoAnimalito tipo = TipoAnimalito.PERRO;

		JSONObject objetoJson = new JSONObject();
		objetoJson.put("edad", edad);
		objetoJson.put("tipo", tipo);
		
		ResultActions resultado = clienteWeb.perform(MockMvcRequestBuilders.post("/api/v1/animalitos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objetoJson.toString()));
		resultado.andExpect(status().isBadRequest());
	}

}
 
