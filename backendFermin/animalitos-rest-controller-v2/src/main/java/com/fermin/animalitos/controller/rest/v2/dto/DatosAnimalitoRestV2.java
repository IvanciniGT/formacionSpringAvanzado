package com.fermin.animalitos.controller.rest.v2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class DatosAnimalitoRestV2 extends DatosDeNuevoAnimalitoRestV2{

	private Long id;
	
}
