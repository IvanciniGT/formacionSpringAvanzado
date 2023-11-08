package com.fermin.animalitos.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.mapstruct.Mapper;

import com.fermin.animalitos.entity.Animalito;
import com.fermin.animalitos.repository.AnimalitosRepository;
import com.fermin.animalitos.service.dto.DatosAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;
import com.fermin.animalitos.service.event.NotificacionServicioAnimalitos;
import com.fermin.emails.service.EmailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnimalitosServiceImpl implements AnimalitosService {

	private final AnimalitosRepository respositorioDeAnimalitos;
	private final EmailsService servicioDeEmails;
	
	DatosAnimalito altaDeAnimalito(DatosDeNuevoAnimalito datosDeNuevoAnimalito) {
		// Persistir el animalito, compatibilizando los datos
		Animalito animalito = toAnimalito(datosDeNuevoAnimalito);
		respositorioDeAnimalitos.save(animalito);
		// Manderé un email
		// Mandaré una notificacion de tipo alta de animalito
		// Devolver los datos con el formato adecuado	
	}

	Optional<DatosAnimalito> getAnimalito(Long ultimoIdDeAnimalito){
		
	}

	List<DatosAnimalito> getAllAnimalitos(){
		
	}

	void subscribe(Consumer<NotificacionServicioAnimalitos> consumidor) {
		
	}
	
	private static final Animalito toAnimalito(DatosDeNuevoAnimalito datosDeNuevoAnimalito) {
		Animalito a = new Animalito();
		a.setNombre(datosDeNuevoAnimalito.getNombre());
		a.setTipo(datosDeNuevoAnimalito.getTipo());
		a.setEdad(datosDeNuevoAnimalito.getEdad());
		return a;
	}//MapStruct
	

	@Mapper
	interface AnimalitosServiceMapper {
		Animalito toAnimalito(DatosDeNuevoAnimalito datosDeNuevoAnimalito);		
	}
}

