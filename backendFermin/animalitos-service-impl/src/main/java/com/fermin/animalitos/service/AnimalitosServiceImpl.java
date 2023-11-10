package com.fermin.animalitos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fermin.animalitos.entity.Animalito;
import com.fermin.animalitos.repository.AnimalitosRepository;
import com.fermin.animalitos.service.dto.DatosAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;
import com.fermin.animalitos.service.event.NotificacionServicioAnimalitos;
import com.fermin.animalitos.service.event.NotificacionServicioAnimalitos.TipoNotificacion;
import com.fermin.animalitos.service.mapper.AnimalitosServiceMapper;
import com.fermin.emails.service.EmailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnimalitosServiceImpl implements AnimalitosService {

	private final AnimalitosRepository respositorioDeAnimalitos;
	private final EmailsService servicioDeEmails;
	private final AnimalitosServiceMapper mapeador; 

	//Inversión de dependencias: Los módulos de alta nivel no deben depender de implementaciones concretas de los modulos de bajo nivel
	// Inyección de dependencias: Las clases no crean instancias de los objetos que necesitan.. sino que le son suministradas
	
	private final List<Consumer<NotificacionServicioAnimalitos>> consumidores = new ArrayList<>();
	
	@Value("${animalitos.service.email.destinatario:altas@animalitosfermin.com}")
	private String destinatario;
	@Value("${animalitos.service.email.asunto:Nuevo animalito}")
	private String asunto;
	@Value("${animalitos.service.email.contenido:Se ha dado de alta al animalito: }")
	private String contenido;

	
	// En el servidor de qaplicaciones (TOMCAT) se configura un ExecutorPool -> pool de hilos
	// Cada petición HTTP toma un hilo de ese pool de hilos
	// Y ese hilo se usa para ejecuatr el código
	
	@Override
	public DatosAnimalito altaDeAnimalito(DatosDeNuevoAnimalito datosDeNuevoAnimalito) {
		// Persistir el animalito, compatibilizando los datos
		Animalito animalito = mapeador.toAnimalito(datosDeNuevoAnimalito);
		Animalito animalitoPersistido = respositorioDeAnimalitos.save(animalito);
		// Manderé un email
		servicioDeEmails.enviarEmail(destinatario, asunto, contenido + animalitoPersistido.getNombre());
		// Mandaré una notificacion de tipo alta de animalito
		DatosAnimalito datosDelAnimnalito = mapeador.toDatosAnimalito(animalitoPersistido);
		this.mandarNotificacion(datosDelAnimnalito, NotificacionServicioAnimalitos.TipoNotificacion.ALTA_ANIMALITO);
		// Devolver los datos con el formato adecuado
		return datosDelAnimnalito;
	}

	private void mandarNotificacion(DatosAnimalito datosDelAnimnalito, TipoNotificacion altaAnimalito) {
		final NotificacionServicioAnimalitos notificacion = new NotificacionServicioAnimalitos(altaAnimalito, datosDelAnimnalito);
		this.consumidores.forEach(consumidor -> consumidor.accept(notificacion));
		//for(Consumer<NotificacionServicioAnimalitos> consumidor:this.consumidores){
		//	consumidor.accept(notificacion);
		//}
	}

	@Override
	public Optional<DatosAnimalito> getAnimalito(Long id){
		Optional<Animalito> animalitoRecuperado = respositorioDeAnimalitos.findById(id);
		if(animalitoRecuperado.isPresent())
			return Optional.of(mapeador.toDatosAnimalito(animalitoRecuperado.get()));
		return Optional.empty();
	}

	@Override
	public List<DatosAnimalito> getAllAnimalitos(){
		return respositorioDeAnimalitos.findAll().stream().map( mapeador::toDatosAnimalito ).collect(Collectors.toList());
	}

	@Override
	public void subscribe(Consumer<NotificacionServicioAnimalitos> consumidor) {
		this.consumidores.add(consumidor);
	}
	
	@Override
	public void unsubscribe(Consumer<NotificacionServicioAnimalitos> consumidor) {
		this.consumidores.remove(consumidor);
	}

}








