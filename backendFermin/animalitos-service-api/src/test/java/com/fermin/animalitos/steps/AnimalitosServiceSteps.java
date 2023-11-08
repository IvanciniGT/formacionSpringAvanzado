package com.fermin.animalitos.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

public class AnimalitosServiceSteps {
	@Dado("Un servicio de Animalitos configurado")
	public void un_servicio_de_animalitos_configurado() {
	}

	@Dado("Un repositorio de Animalitos configurado")
	public void un_repositorio_de_animalitos_configurado() {
	}

	@Dado("Un servicio de Emails configurado")
	public void un_servicio_de_emails_configurado() {
	}

	@Dado("unos datos de un nuevo animalito")
	public void unos_datos_de_un_nuevo_animalito() {
	}

	@Dado("ese nuevo animalito tiene por {string}: {string}")
	public void ese_nuevo_animalito_tiene_por(String campo, String valor) {
	}

	@Dado("ese nuevo animalito tiene por {string}: {int}")
	public void ese_nuevo_animalito_tiene_por(String campo, Integer valor) {
	}

	@Cuando("se solicita al servicio de animalitos el alta de ese nuevo animalito")
	public void se_solicita_al_servicio_de_animalitos_el_alta_de_ese_nuevo_animalito() {
	}

	@Entonces("se devuelven los datos del animalito")
	public void se_devuelven_los_datos_del_animalito() {
	}

	@Entonces("los datos del animalito tienen un id mayor que cero")
	public void los_datos_del_animalito_tienen_un_id_mayor_que_cero() {
	}

	@Entonces("los datos del animalito tienen por {string}: {string}")
	public void los_datos_del_animalito_tienen_por(String campo, String valor) {
	}

	@Entonces("los datos del animalito tienen por {string}: {int}")
	public void los_datos_del_animalito_tienen_por(String campo, Integer valor) {
	}

	@Entonces("en el repositorio de animalitos se ha guardado un animalito con el id devuelto")
	public void en_el_repositorio_de_animalitos_se_ha_guardado_un_animalito_con_el_id_devuelto() {
	}

	@Entonces("el animalito en el repositorio de animalitos tiene por {string} {string}")
	public void el_animalito_en_el_repositorio_de_animalitos_tiene_por(String campo, String valor) {
	}

	@Entonces("el animalito en el repositorio de animalitos tiene por {string} {int}")
	public void el_animalito_en_el_repositorio_de_animalitos_tiene_por(String campo, Integer valor) {
	}

/*	@Entonces("se debe recibir en la bandeja de entrada {string} del usuario {string} un correo")
	public void se_debe_recibir_en_la_bandeja_de_entrada_del_usuario_un_correo(String bandeja, String destinatario) {
	}

	@Entonces("el correo debe tener por asunto: {string}")
	public void el_correo_debe_tener_por_asunto(String asunto) {
	}

	@Entonces("el cuerpo del correo debe contener: {string}")
	public void el_cuerpo_del_correo_debe_contener(String cuerpo) {
	}
*/
	@Dado("se devuelven los datos de animalito")
	public void se_devuelven_los_datos_de_animalito() {
	}

	@Cuando("se solicita un animalito con el id anterior al servicio de animalitos")
	public void se_solicita_un_animalito_con_el_id_anterior_al_servicio_de_animalitos() {
	}

	@Entonces("los datos del animalito tienen por id el anterior")
	public void los_datos_del_animalito_tienen_por_id_el_anterior() {
	}

	@Dado("que estoy subscrito a los eventos del servicio de animalitos")
	public void que_estoy_subscrito_a_los_eventos_del_servicio_de_animalitos() {
	}

	@Entonces("se recibe una notificación de un nuevo evento del servicio de animalitos")
	public void se_recibe_una_notificación_de_un_nuevo_evento_del_servicio_de_animalitos() {
	}

	@Entonces("la notificación es de tipo: {string}")
	public void la_notificación_es_de_tipo(String tipoNotificacion) {
	}

	@Entonces("la notificación es para un animalito")
	public void la_notificación_es_para_un_animalito() {
	}

	@Entonces("ese animalito de la notificación tiene por id el anterior")
	public void ese_animalito_de_la_notificación_tiene_por_id_el_anterior() {
	}

	@Entonces("ese animalito de la notificación tiene por {string}: {string}")
	public void ese_animalito_de_la_notificación_tiene_por(String campo, String valor) {
	}

	@Entonces("ese animalito de la notificación tiene por {string}: {int}")
	public void ese_animalito_de_la_notificación_tiene_por(String campo, Integer valor) {
	}


}
