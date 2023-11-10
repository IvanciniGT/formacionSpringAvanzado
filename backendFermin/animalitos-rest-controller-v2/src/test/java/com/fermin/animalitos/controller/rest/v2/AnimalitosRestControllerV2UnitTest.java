package com.fermin.animalitos.controller.rest.v2;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fermin.TestApplication;
import com.fermin.animalitos.controller.rest.v2.AnimalitosControllerRestV2;
import com.fermin.animalitos.controller.rest.v2.dto.DatosAnimalitoRestV2;
import com.fermin.animalitos.controller.rest.v2.dto.DatosDeNuevoAnimalitoRestV2;
import com.fermin.animalitos.entity.TipoAnimalito;
import com.fermin.animalitos.service.AnimalitosService;
import com.fermin.animalitos.service.dto.DatosAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;

@ExtendWith(SpringExtension.class)					// Que JUnit PUEDA solicitar datos a Spring
@SpringBootTest(classes = TestApplication.class)	// Arranca una app que he de tener configurada (De antemano) para que incluya mi Servicio
//@TestInstance(Lifecycle.PER_CLASS) Con esta anotación, JUnit crea una úinica instancia de la clase en la que ejecuta todas las funciones de test
// Esto es lo que necesito si quiero meter un Hook de JUNIT (Before... After...) que trabaje con datos a nivel de instancia
public class AnimalitosRestControllerV2UnitTest {

	@MockBean
	private AnimalitosService animalitosService;
	@Captor
	private ArgumentCaptor<DatosDeNuevoAnimalito> datosRecibidosPorServicio;
	
	private final AnimalitosControllerRestV2 controlador;
	
	
	AnimalitosRestControllerV2UnitTest(@Autowired AnimalitosControllerRestV2 controlador){
		this.controlador=controlador;
	}

	@Test
	@DisplayName("Alta de nuevo animalito con datos guays")
	void altaGuay() {
		String nombre = "firulais";
		int edad = 1;
		TipoAnimalito tipo = TipoAnimalito.PERRO;
		String color = "rojo";
		DatosDeNuevoAnimalitoRestV2 datos = new DatosDeNuevoAnimalitoRestV2(nombre,tipo, edad, color);
		DatosAnimalito datosQueDevuelveElServicio = DatosAnimalito.builder().id(33L).nombre(nombre).tipo(tipo).edad(edad).color(color).build();
		when(animalitosService.altaDeAnimalito(any(DatosDeNuevoAnimalito.class))).thenReturn(datosQueDevuelveElServicio);
		ResponseEntity<DatosAnimalitoRestV2> datosDevueltos = controlador.altaDeAnimalito(datos);
		Assertions.assertEquals(HttpStatus.CREATED, datosDevueltos.getStatusCode());
		Assertions.assertEquals(nombre, datosDevueltos.getBody().getNombre());
		Assertions.assertEquals(tipo, datosDevueltos.getBody().getTipo());
		Assertions.assertEquals(edad, datosDevueltos.getBody().getAge());
		verify(animalitosService).altaDeAnimalito(datosRecibidosPorServicio.capture());
		Assertions.assertEquals(nombre, datosRecibidosPorServicio.getValue().getNombre());
		Assertions.assertEquals(tipo, datosRecibidosPorServicio.getValue().getTipo());
		Assertions.assertEquals(edad, datosRecibidosPorServicio.getValue().getEdad());
		Assertions.assertEquals(color, datosRecibidosPorServicio.getValue().getColor());
	}

	@Test
	@DisplayName("Alta de nuevo animalito con datos ruina sin nombre")
	void altaRuina1() {
		String nombre = null;
		int edad = 1;
		TipoAnimalito tipo = TipoAnimalito.PERRO;
		DatosDeNuevoAnimalitoRestV2 datos = new DatosDeNuevoAnimalitoRestV2(nombre, tipo, edad, "verde");
		ResponseEntity<DatosAnimalitoRestV2> datosDevueltos = controlador.altaDeAnimalito(datos);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, datosDevueltos.getStatusCode());
		Assertions.assertNull(datosDevueltos.getBody());
	}

	@Test
	@DisplayName("Alta de nuevo animalito con datos ruina con nomnbre vacio")
	void altaRuina2() {
		String nombre = " ";
		int edad = 1;
		TipoAnimalito tipo = TipoAnimalito.PERRO;
		DatosDeNuevoAnimalitoRestV2 datos = new DatosDeNuevoAnimalitoRestV2(nombre, tipo, edad, "verde");
		ResponseEntity<DatosAnimalitoRestV2> datosDevueltos = controlador.altaDeAnimalito(datos);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, datosDevueltos.getStatusCode());
		Assertions.assertNull(datosDevueltos.getBody());
	}

	@Test
	@DisplayName("Alta de nuevo animalito con datos ruina sin tipo")
	void altaRuina3() {
		String nombre = null;
		int edad = 1;
		TipoAnimalito tipo = null;
		DatosDeNuevoAnimalitoRestV2 datos = new DatosDeNuevoAnimalitoRestV2(nombre, tipo, edad, "azul");
		ResponseEntity<DatosAnimalitoRestV2> datosDevueltos = controlador.altaDeAnimalito(datos);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, datosDevueltos.getStatusCode());
		Assertions.assertNull(datosDevueltos.getBody());
	}
	

}
 
