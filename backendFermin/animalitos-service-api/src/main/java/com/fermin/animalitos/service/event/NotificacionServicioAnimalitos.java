package com.fermin.animalitos.service.event;

import com.fermin.animalitos.service.dto.DatosAnimalito;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionServicioAnimalitos {

	public static enum TipoNotificacion {
		ALTA_ANIMALITO,
		BAJA_ANIMALITO,
		MODIFICACION_ANIMALITO
	}

	private TipoNotificacion tipo;
	private DatosAnimalito datosAnimalito;
}
