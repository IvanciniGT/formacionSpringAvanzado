package com.fermin.animalitos.controller.rest.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class DatosAnimalitoRestV1 extends DatosDeNuevoAnimalitoRestV1{

	private Long id;
	
}
