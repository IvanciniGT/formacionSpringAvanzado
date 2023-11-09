package com.fermin.animalitos.service.dto;

import com.fermin.animalitos.entity.TipoAnimalito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class DatosDeNuevoAnimalito {

	private String nombre;
	private TipoAnimalito tipo;
	private Integer edad;
	
}
