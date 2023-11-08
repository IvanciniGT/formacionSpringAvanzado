package com.fermin.animalitos.service.event;

import com.fermin.animalitos.service.dto.DatosAnimalito;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class NotificacionServicioAnimalitos {

	public static enum TipoNotificacion {
		ALTA_ANIMALITO,
		BAJA_ANIMALITO,
		MODIFICACION_ANIMALITO
	}

	TipoNotificacion tipo;
	DatosAnimalito datosAnimalito;
}
