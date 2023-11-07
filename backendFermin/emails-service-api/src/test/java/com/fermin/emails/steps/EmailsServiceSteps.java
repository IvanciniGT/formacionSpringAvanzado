package com.fermin.emails.steps;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import com.fermin.emails.service.EmailsService;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

public class EmailsServiceSteps {

	@Autowired
	private EmailsService servicioDeEmails;
	
	private String destinatario;
	private String asunto;
	private String cuerpo;
	private Exception excepcionLanzada;
	
	@Dado("un servicio de emails configurado")
	public void un_servicio_de_emails_configurado() {
		Assertions.assertNotNull(servicioDeEmails);
	}
	
	@Dado("un destinatario: {string}")
	public void un_destinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	@Dado("un asunto: {string}")
	public void un_asunto(String asunto) {
		this.asunto = asunto;
	}
	
	@Dado("un cuerpo: {string}")
	public void un_cuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	
	@Cuando("solicito el envío de un email con los datos anteriores")
	public void solicito_el_envío_de_un_email_con_los_datos_anteriores() {
		try {
			this.servicioDeEmails.enviarEmail(destinatario, asunto, cuerpo);
		}catch(Exception excepcionLanzada) {
			this.excepcionLanzada=excepcionLanzada;
		}
	}
	
	@Entonces("el email se envía sin problemas")
	public void el_email_se_envía_sin_problemas() {
		Assertions.assertNull(excepcionLanzada);
	}
	
	@Entonces("se debe recibir en la bandeja de entrada {string} del usuario {string} un correo")
	public void se_debe_recibir_en_la_bandeja_de_entrada_del_usuario_un_correo(String tipoBuzon, String destinatario) {
		// CODIGO
	}
	
	@Entonces("el correo debe tener por asunto: {string}")
	public void el_correo_debe_tener_por_asunto(String asunto) {
		// CODIGO
	}
	
	@Entonces("el cuerpo del correo debe contener: {string}")
	public void el_cuerpo_del_correo_debe_contener(String cuerpo) {
		// CODIGO
	}
	
	@Entonces("me da un error al mandar el email")
	public void me_da_un_error_al_mandar_el_email() {
		Assertions.assertNotNull(excepcionLanzada);
	}

}

