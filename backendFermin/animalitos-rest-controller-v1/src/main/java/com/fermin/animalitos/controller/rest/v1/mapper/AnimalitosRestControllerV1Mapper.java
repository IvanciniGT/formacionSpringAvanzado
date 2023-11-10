package com.fermin.animalitos.controller.rest.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fermin.animalitos.controller.rest.v1.dto.DatosAnimalitoRestV1;
import com.fermin.animalitos.controller.rest.v1.dto.DatosDeNuevoAnimalitoRestV1;
import com.fermin.animalitos.service.dto.DatosAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;

@Mapper
public interface AnimalitosRestControllerV1Mapper {
//	@Mappings({
//		@Mapping (source = "nombre", target = "name")
//	})
	@Mapping(target = "color", defaultValue = "desconocido")
	DatosDeNuevoAnimalito toDatosNuevoAnimalito(DatosDeNuevoAnimalitoRestV1 datosDeNuevoAnimalito);		
	DatosAnimalitoRestV1 toDatosAnimalito(DatosAnimalito animalito);		
}
