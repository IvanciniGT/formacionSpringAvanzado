package com.fermin.animalitos.controller.rest.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fermin.animalitos.controller.rest.v2.dto.DatosAnimalitoRestV2;
import com.fermin.animalitos.controller.rest.v2.dto.DatosDeNuevoAnimalitoRestV2;
import com.fermin.animalitos.service.dto.DatosAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;

@Mapper
public interface AnimalitosRestControllerV2Mapper {

	@Mapping(source="age", target= "edad")
	DatosDeNuevoAnimalito toDatosNuevoAnimalito(DatosDeNuevoAnimalitoRestV2 datosDeNuevoAnimalito);		
	
	@Mapping(source="edad", target= "age")
	DatosAnimalitoRestV2 toDatosAnimalito(DatosAnimalito animalito);		
}
