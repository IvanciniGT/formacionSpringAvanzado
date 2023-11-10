package com.fermin.animalitos.controller.rest.v2.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalitosRestControllerV2MapperConfiguration {

	@Bean
	public AnimalitosRestControllerV2Mapper configurarInstanciaDelAnimalitosRestControllerV2Mapper() {
		return Mappers.getMapper(AnimalitosRestControllerV2Mapper.class);
	}
	
}
/*
 Cuando Spring arranque, buscará todas las clases con anotación Configuration.
 Crea una instancia de ellas
 Busca dentro de ellas los métodos con anotación @Bean
 Los invoca
 Cache el resultado de cada método
 A partir de ese momento si alguien solicita un dato del tipo devuelto por alguna de esas funciones con anotación @Bean
 Spring devolverá lo que le devolvió la función cuando la llamo, y que guardó en su cache.
 El nombre de las funciones con @Bean es indiferentes. Nosotros NUNCA vamos a llamar a esas funciones. 
 Spring es quién las llama... y las llama por tener anotaciuón @Bean... Ya se encarga él de averiguar el nombre.
 */