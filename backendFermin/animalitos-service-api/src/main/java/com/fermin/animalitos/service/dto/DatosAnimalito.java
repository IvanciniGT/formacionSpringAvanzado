package com.fermin.animalitos.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DatosAnimalito extends DatosDeNuevoAnimalito{

	private Long id;
	
}
