package com.fermin.animalitos.controller.rest.v1.dto;

import com.fermin.animalitos.entity.TipoAnimalito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosDeNuevoAnimalitoRestV1 {

	private String nombre;
	private TipoAnimalito tipo;
	private Integer edad;
	
}
