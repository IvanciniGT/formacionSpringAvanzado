package com.fermin.animalitos.controller.rest.v1.dto;

import com.fermin.animalitos.entity.TipoAnimalito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class DatosDeNuevoAnimalitoRestV1 {

	public DatosDeNuevoAnimalitoRestV1(String nombre,TipoAnimalito tipo,Integer edad) {
		this.nombre=nombre;
		this.tipo=tipo; 
		this.edad=edad;
	}
	
	@Setter	private String nombre;
	@Setter	private TipoAnimalito tipo;
	@Setter	private Integer edad;
	private String color;
}
