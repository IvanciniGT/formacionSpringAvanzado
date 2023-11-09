package com.fermin.animalitos.controller.rest.v1;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fermin.animalitos.controller.rest.v1.dto.DatosAnimalitoRestV1;
import com.fermin.animalitos.controller.rest.v1.dto.DatosDeNuevoAnimalitoRestV1;
import com.fermin.animalitos.controller.rest.v1.mapper.AnimalitosRestControllerV1Mapper;
import com.fermin.animalitos.service.AnimalitosService;
import com.fermin.animalitos.service.dto.DatosAnimalito;

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
@RequestMapping("/api/v1") // Esta se usa como prefijo en las de abajo
public class AnimalitosControllerRestV1 {

	private final AnimalitosRestControllerV1Mapper mapeador;
	private final AnimalitosService animalitosService;
	
	// Spring en autom, tomará los datos del JSON que llegue en el body HTTP del request y los convierta en un objeto de tipo DatosDeNuevoAnimalito
	// Spring en autom, tomará los datos del objeto DatosAnimalito que devolvamos, los convierte a JSON y los añade al body del response http
	@PostMapping("/animalitos")
	ResponseEntity<DatosAnimalitoRestV1> altaDeAnimalito(@RequestBody DatosDeNuevoAnimalitoRestV1 datosDeNuevoAnimalito){
		// Validación de cortesía
		if(datosDeNuevoAnimalito.getNombre()!=null 
				&& !datosDeNuevoAnimalito.getNombre().trim().isEmpty()
				&& datosDeNuevoAnimalito.getTipo()!=null)
			try {
				DatosAnimalito persistido = animalitosService.altaDeAnimalito(mapeador.toDatosNuevoAnimalito(datosDeNuevoAnimalito));
				return new ResponseEntity<>(mapeador.toDatosAnimalito(persistido), HttpStatus.CREATED);
			}catch(Exception e) {
			}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/animalitos/{id}")
	ResponseEntity<DatosAnimalitoRestV1> getAnimalito(@PathVariable("id") Long id){
		DatosAnimalitoRestV1 toReturn=null;
		if(id>=0) { // Siempre y cuando tenga muy claro que ese id no puede existir
			Optional<DatosAnimalito> posiblesDatosDeAnimalito = animalitosService.getAnimalito(id); 
			if(posiblesDatosDeAnimalito.isPresent())
				toReturn = mapeador.toDatosAnimalito(posiblesDatosDeAnimalito.get());
		}
		return new ResponseEntity<DatosAnimalitoRestV1>(toReturn, toReturn==null?HttpStatus.NOT_FOUND:HttpStatus.OK);
	}

	@GetMapping("/animalitos")
	ResponseEntity<List<DatosAnimalitoRestV1>> getAllAnimalitos(){
		return new ResponseEntity<>(animalitosService.getAllAnimalitos().stream().map( mapeador::toDatosAnimalito).collect(Collectors.toList()), HttpStatus.OK);
	}
	
}
