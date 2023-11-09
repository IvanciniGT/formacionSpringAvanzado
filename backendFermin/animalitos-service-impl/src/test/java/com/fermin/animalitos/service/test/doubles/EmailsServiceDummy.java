package com.fermin.animalitos.service.test.doubles;

import org.springframework.stereotype.Component;

import com.fermin.emails.service.EmailsService;

@Component
// NOTA: ESTA CLASE YA NO SERIA NECESARIA , AL HABER CONFIGURADO UN MOCK... El MOCK REEMPLAZA A ESTA CLASE 
public class EmailsServiceDummy implements EmailsService{

	public EmailsServiceDummy() {
		System.out.println("SOY EL SERVICIO DE EMAILS DUMMY");
	}
	
	@Override
	public void enviarEmail(String destinatario, String asunto, String cuerpo) {
	}

}
