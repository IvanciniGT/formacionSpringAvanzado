package com.fermin.animalitos.service.dto;

import com.fermin.animalitos.entity.TipoAnimalito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosDeNuevoAnimalito {

	private String nombre;
	private TipoAnimalito tipo;
	private Integer edad;
	
}
