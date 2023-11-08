package com.fermin.animalitos.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.fermin.animalitos.service.dto.DatosAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;
import com.fermin.animalitos.service.event.NotificacionServicioAnimalitos;

public interface AnimalitosService {

	DatosAnimalito altaDeAnimalito(DatosDeNuevoAnimalito datosDeNuevoAnimalito);

	Optional<DatosAnimalito> getAnimalito(Long ultimoIdDeAnimalito);

	List<DatosAnimalito> getAllAnimalitos();

	void subscribe(Consumer<NotificacionServicioAnimalitos> consumidor);

	void unsubscribe(Consumer<NotificacionServicioAnimalitos> consumidor);
	
}
