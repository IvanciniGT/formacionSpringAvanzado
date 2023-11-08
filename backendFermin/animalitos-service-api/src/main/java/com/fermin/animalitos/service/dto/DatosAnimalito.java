package com.fermin.animalitos.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class DatosAnimalito extends DatosDeNuevoAnimalito{

	private Long id;
	
}
