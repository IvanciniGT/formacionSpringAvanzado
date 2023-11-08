package com.fermin.animalitos.steps;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import com.fermin.animalitos.entity.Animalito;
import com.fermin.animalitos.repository.AnimalitosRepository;
import com.fermin.animalitos.service.AnimalitosService;
import com.fermin.animalitos.service.dto.DatosAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;
import com.fermin.animalitos.service.event.NotificacionServicioAnimalitos;
import com.fermin.emails.service.EmailsService;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

public class AnimalitosServiceSteps {

	@Autowired
	private AnimalitosService animalitosService;

	@Autowired
	private AnimalitosRepository animalitosRepository;

	@Autowired
	private EmailsService emailsService;
	
	private DatosDeNuevoAnimalito datosDeNuevoAnimalito;
	private DatosAnimalito datosAnimalito;
	private Long ultimoIdDeAnimalito;
	private Animalito animalitoDevuelto;
	private NotificacionServicioAnimalitos notificacionRecibida;
	
	@Dado("Un servicio de Animalitos configurado")
	public void un_servicio_de_animalitos_configurado() {
		Assertions.assertNotNull(animalitosService);
	}

	@Dado("Un repositorio de Animalitos configurado")
	public void un_repositorio_de_animalitos_configurado() {
		Assertions.assertNotNull(animalitosRepository);
	}

	@Dado("Un servicio de Emails configurado")
	public void un_servicio_de_emails_configurado() {
		Assertions.assertNotNull(emailsService);
	}

	@Dado("unos datos de un nuevo animalito")
	public void unos_datos_de_un_nuevo_animalito() {
		this.datosDeNuevoAnimalito = new DatosDeNuevoAnimalito();
	}

	@Dado("ese nuevo animalito tiene por {string}: {string}")
	public void ese_nuevo_animalito_tiene_por(String campo, String valor) {
		switch(campo.toLowerCase()) {
			case "nombre":
				this.datosDeNuevoAnimalito.setNombre(valor);
				break;
			//case "tipo":
			//	this.datosDeNuevoAnimalito.setTipo(valor);
		}	
	}

	@Dado("ese nuevo animalito tiene por {string}: {int}")
	public void ese_nuevo_animalito_tiene_por(String campo, Integer valor) {
		switch(campo.toLowerCase()) {
		case "edad":
			this.datosDeNuevoAnimalito.setEdad(valor);
		}
	}

	@Cuando("se solicita al servicio de animalitos el alta de ese nuevo animalito")
	public void se_solicita_al_servicio_de_animalitos_el_alta_de_ese_nuevo_animalito() {
		this.datosAnimalito=animalitosService.altaDeAnimalito(datosDeNuevoAnimalito);
		this.ultimoIdDeAnimalito=datosAnimalito.getId();
	}

	@Entonces("se devuelven los datos del animalito")
	public void se_devuelven_los_datos_del_animalito() {
		Assertions.assertNotNull(datosAnimalito);
	}

	@Entonces("los datos del animalito tienen un id mayor que cero")
	public void los_datos_del_animalito_tienen_un_id_mayor_que_cero() {
		Assertions.assertTrue(datosAnimalito.getId() > 0);
	}

	@Entonces("los datos del animalito tienen por {string}: {string}")
	public void los_datos_del_animalito_tienen_por(String campo, String valor) {
		switch(campo.toLowerCase()) {
		case "nombre":
			Assertions.assertEquals(valor, datosAnimalito.getNombre());
			break;
		case "tipo":
			Assertions.assertEquals(valor, datosAnimalito.getTipo());
		}
	}

	@Entonces("los datos del animalito tienen por {string}: {int}")
	public void los_datos_del_animalito_tienen_por(String campo, Integer valor) {
		switch(campo.toLowerCase()) {
		case "edad":
			Assertions.assertEquals(valor, datosAnimalito.getEdad());
		}
	}

	@Entonces("en el repositorio de animalitos se ha guardado un animalito con el id devuelto")
	public void en_el_repositorio_de_animalitos_se_ha_guardado_un_animalito_con_el_id_devuelto() {
	//	this.animalitoDevuelto = animalitosRepository.findById(ultimoIdDeAnimalito).get();
		
	}

	@Entonces("el animalito en el repositorio de animalitos tiene por {string} {string}")
	public void el_animalito_en_el_repositorio_de_animalitos_tiene_por(String campo, String valor) {
		switch(campo.toLowerCase()) {
		case "nombre":
			Assertions.assertEquals(valor, animalitoDevuelto.getNombre());
			break;
		case "tipo":
			Assertions.assertEquals(valor, animalitoDevuelto.getTipo());
		}
	}

	@Entonces("el animalito en el repositorio de animalitos tiene por {string} {int}")
	public void el_animalito_en_el_repositorio_de_animalitos_tiene_por(String campo, Integer valor) {
		switch(campo.toLowerCase()) {
		case "edad":
			Assertions.assertEquals(valor, animalitoDevuelto.getEdad());
		}
	}

	@Cuando("se solicita un animalito con el id anterior al servicio de animalitos")
	public void se_solicita_un_animalito_con_el_id_anterior_al_servicio_de_animalitos() {
		this.datosAnimalito = animalitosService.getAnimalito(ultimoIdDeAnimalito).get();
	}

	@Entonces("los datos del animalito tienen por id el anterior")
	public void los_datos_del_animalito_tienen_por_id_el_anterior() {
		Assertions.assertEquals(ultimoIdDeAnimalito, datosAnimalito.getId());
	}

	@Dado("que estoy subscrito a los eventos del servicio de animalitos")
	public void que_estoy_subscrito_a_los_eventos_del_servicio_de_animalitos() {
		animalitosService.subscribe( (notificacion) -> this.notificacionRecibida=notificacion );
	}

	@Entonces("se recibe una notificación de un nuevo evento del servicio de animalitos")
	public void se_recibe_una_notificación_de_un_nuevo_evento_del_servicio_de_animalitos() {
		Assertions.assertNotNull(notificacionRecibida);
	}

	@Entonces("la notificación es de tipo: {string}")
	public void la_notificación_es_de_tipo(String tipoNotificacion) {
		switch(tipoNotificacion.toLowerCase()) {
		case "nuevo animalito":
			Assertions.assertEquals(NotificacionServicioAnimalitos.TipoNotificacion.ALTA_ANIMALITO, notificacionRecibida.getTipo());
			break;
		case "baja animalito":
			Assertions.assertEquals(NotificacionServicioAnimalitos.TipoNotificacion.BAJA_ANIMALITO, notificacionRecibida.getTipo());
			break;
		case "modificacion animalito":
			Assertions.assertEquals(NotificacionServicioAnimalitos.TipoNotificacion.MODIFICACION_ANIMALITO, notificacionRecibida.getTipo());
		}
	}

	@Entonces("la notificación es para un animalito")
	public void la_notificación_es_para_un_animalito() {
		Assertions.assertNotNull(notificacionRecibida.getDatosAnimalito());
	}

	@Entonces("ese animalito de la notificación tiene por id el anterior")
	public void ese_animalito_de_la_notificación_tiene_por_id_el_anterior() {
		Assertions.assertEquals(ultimoIdDeAnimalito, notificacionRecibida.getDatosAnimalito().getId());
	}

	@Entonces("ese animalito de la notificación tiene por {string}: {string}")
	public void ese_animalito_de_la_notificación_tiene_por(String campo, String valor) {
		switch(campo.toLowerCase()) {
		case "nombre":
			Assertions.assertEquals(valor, notificacionRecibida.getDatosAnimalito().getNombre());
			break;
		case "tipo":
			Assertions.assertEquals(valor, notificacionRecibida.getDatosAnimalito().getTipo());
		}
	}

	@Entonces("ese animalito de la notificación tiene por {string}: {int}")
	public void ese_animalito_de_la_notificación_tiene_por(String campo, Integer valor) {
		switch(campo.toLowerCase()) {
		case "edad":
			Assertions.assertEquals(valor, notificacionRecibida.getDatosAnimalito().getEdad());
		}
	}


}
