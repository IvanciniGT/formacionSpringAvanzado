package com.fermin.animalitos.service.mapper;

import org.mapstruct.Mapper;

import com.fermin.animalitos.entity.Animalito;
import com.fermin.animalitos.service.dto.DatosAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;

@Mapper
public interface AnimalitosServiceMapper {
	Animalito toAnimalito(DatosDeNuevoAnimalito datosDeNuevoAnimalito);		
	DatosAnimalito toDatosAnimalito(Animalito animalito);		
}
