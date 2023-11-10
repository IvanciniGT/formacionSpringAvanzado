package com.fermin;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fermin.animalitos.repository.AnimalitosRepository;
import com.fermin.emails.service.EmailsService;

import io.cucumber.java.en.Given;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.RequiredArgsConstructor;
import static org.hamcrest.Matchers.*;

@Suite // Esto es una suite de pruebas de JUnit... Se debe ejecutar con JUnit
@IncludeEngines("cucumber") //Le indico a JUnit con qué herramienta debe ejecutar las pruebas de este fichero
@SelectClasspathResource("features1") // Le indico a Junit, que cuando avise al Cucumber, que le pase los ficheros  de la carpeta "features"
@RequiredArgsConstructor
@SpringBootTest(classes = FerminBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)	// Arranca una app que he de tener configurada (De antemano) para que incluya mi Servicio
@AutoConfigureMockMvc
@CucumberContextConfiguration // Permite a Cucumber integrarse con Spring... y usar Beans de Spring

// Maven quiero que lance las pruebas... pero maven las pruebas las ejecuta a través del plugin surefire
// Y surefire sabe ejecutar pruebas de Cucumber? NO.. ejecuta pruebas de JUNIT
// Y el surefire, además de ejecutar pruebas de JUNIT hace otra cosa: Genera un informe de pruebas JUNIT (es un formato estandar en la industria)
// Junit delegará la ejecución de este fichero a Cucumber
// Cucumber genera una instancia de esta clase (pidiendole datos a Spring)
// Cucumber lee el fichero Features
// Y ejecuta ese fichero features... buscando las funciones asociadas en este fichero (glue-code)
public class ServicioRestV1AnimalitosStepsTest {
	
	private final AnimalitosRepository animalitosRepository;
	private final MockMvc clienteWeb;
	
	private JSONObject objetoJson;
	private ResultActions resultado;
	@MockBean
	private EmailsService emailsService;

	@Given("una aplicación con el servicio REST de animalitos en V1")
	@Given("una aplicación con el servicio REST de animalitos en V2")
	public void una_aplicación_con_el_servicio_rest_de_animalitos_en_v1() {
	}
	
	@Dado("un repositorio de animalitos")
	public void un_repositorio_de_animalitos() {
		Assertions.assertNotNull(animalitosRepository);
	}
	
	@Dado("Un cliente web que ataca a nuestra aplicación")
	public void un_cliente_web_que_ataca_a_nuestra_aplicación() {
		Assertions.assertNotNull(clienteWeb);
	}
	
	@Dado("un objeto JSON")
	public void un_objeto_json() {
		objetoJson = new JSONObject();
	}
	
	@Dado("ese objeto JSON tiene por {string}: {string}")
	public void ese_objeto_json_tiene_por(String campo, String valor) throws JSONException {
		objetoJson.put(campo, valor);
	}
	
	@Dado("ese objeto JSON tiene por {string}: {int}")
	public void ese_objeto_json_tiene_por(String campo, Integer valor) throws JSONException {
		objetoJson.put(campo, valor);
	}
	
	@Cuando("se solicita al cliente web un post del objeto JSON al endpoint {string}")
	public void se_solicita_al_cliente_web_un_post_del_objeto_json_al_endpoint(String endpoint) throws Exception {
		resultado = clienteWeb.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objetoJson.toString()));
	}
	
	@Entonces("se devuelve una respuesta http con estado {string}")
	public void se_devuelve_una_respuesta_http_con_estado(String estado) throws Exception {
		switch(estado.toUpperCase()) {
		case "CREATED":
			resultado.andExpect(status().isCreated());
			break;
		case "OK":
			resultado.andExpect(status().isOk());
			break;
		case "NOT_FOUND":
			resultado.andExpect(status().isNotFound());
			break;
		case "BAD_REQUEST":
			resultado.andExpect(status().isBadRequest());
			break;
		}
	}
	
	@Entonces("en el cuerpo de la respuesta hay un objeto JSON")
	public void en_el_cuerpo_de_la_respuesta_hay_un_objeto_json() throws Exception {
		resultado.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Entonces("ese objeto JSON tiene un campo {string} con un vaor numérico mayor que {int}")
	public void ese_objeto_json_tiene_un_campo_con_un_vaor_numérico_mayor_que(String campo, Integer valor) throws Exception {
		resultado.andExpect(jsonPath("$.id").isNumber());
		resultado.andExpect(jsonPath("$.id").value(greaterThan(0)));

	}
	
	@Entonces("ese objeto JSON tiene un campo {string} con valor {string}")
	public void ese_objeto_json_tiene_un_campo_con_valor(String campo, String valor) throws Exception {
		resultado.andExpect(jsonPath("$."+campo).value(valor));
	}
	
	@Entonces("ese objeto JSON tiene un campo {string} con valor {int}")
	public void ese_objeto_json_tiene_un_campo_con_valor(String campo, Integer valor) throws Exception {
		resultado.andExpect(jsonPath("$."+campo).value(valor));
	}

	@Entonces("en el repositorio de animalitos se ha guardado un animalito con el id devuelto")
	public void en_el_repositorio_de_animalitos_se_ha_guardado_un_animalito_con_el_id_devuelto() {
	}
	@Entonces("el animalito en el repositorio de animalitos tiene por {string} {string}")
	public void el_animalito_en_el_repositorio_de_animalitos_tiene_por(String string, String string2) {
	}
	@Entonces("el animalito en el repositorio de animalitos tiene por {string} {int}")
	public void el_animalito_en_el_repositorio_de_animalitos_tiene_por(String string, Integer int1) {
	}
}
