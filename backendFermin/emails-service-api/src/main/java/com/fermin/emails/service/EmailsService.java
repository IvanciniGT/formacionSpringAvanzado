package com.fermin.emails.service;

public interface EmailsService {

	// Esta funcion en caso de recibir null en destinatario, asunto, cuerpo debe devolver una exception NullPointerException
	void enviarEmail(String destinatario, String asunto, String cuerpo);

}
