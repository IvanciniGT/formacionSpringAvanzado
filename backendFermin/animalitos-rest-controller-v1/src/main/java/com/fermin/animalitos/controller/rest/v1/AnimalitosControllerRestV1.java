package com.fermin.animalitos.controller.rest.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fermin.animalitos.controller.rest.v1.dto.DatosAnimalitoRestV1;
import com.fermin.animalitos.controller.rest.v1.dto.DatosDeNuevoAnimalitoRestV1;
import com.fermin.animalitos.controller.rest.v1.mapper.AnimalitosRestControllerV1Mapper;
import com.fermin.animalitos.service.AnimalitosService;

import lombok.RequiredArgsConstructor;

@RestController
// @RestController es un subtipo de @Controller
//		Esta anotación NO SOLO ES SEMANTICA.
//      Por estar la clase anotada con esta anotación, SPRING va a empezar a enrutar las peticiones HTTP que reciba el 
//      servidor de aplicaciones HTTP (tomcat, netty... weblogic) donde Spring esté ejecutándose, a los métodos correspondientes de esta clase.
//		No obstante para que esto funcione, DEBO suministrar la información de ENRUTAMIENTO
// @Controller es un sutipo de la anotación @Component
//      Por ello, Spring automaticamente identificará esta clase como un componente de mi aplicación, que instanciará en automático
//      La anotación @Controler es puramente SEMANTICA... indica a los desarrolaldores que esta clase contiene lógica para exponer servicios
@RequiredArgsConstructor
public class AnimalitosControllerRestV1 {

	private final AnimalitosRestControllerV1Mapper mapeador;
	private final AnimalitosService animalitosService;
	
	// Spring en autom, tomará los datos del JSON que llegue en el body HTTP del request y los convierta en un objeto de tipo DatosDeNuevoAnimalito
	// Spring en autom, tomará los datos del objeto DatosAnimalito que devolvamos, los convierte a JSON y los añade al body del response http
	@PostMapping("/ap1/v1/animalitos")
	ResponseEntity<DatosAnimalitoRestV1> altaDeAnimalito(@RequestBody DatosDeNuevoAnimalitoRestV1 datosDeNuevoAnimalito){
		return null;
	}

	@GetMapping("/ap1/v1/animalitos/{id}")
	ResponseEntity<Optional<DatosAnimalitoRestV1>> getAnimalito(@PathVariable("id") Long ultimoIdDeAnimalito){
		return null;
	}

	@GetMapping("/ap1/v1/animalitos")
	ResponseEntity<List<DatosAnimalitoRestV1>> getAllAnimalitos(){
		return null;
	}
	
}
