package com.fermin.animalitos.controller.rest.v1;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fermin.animalitos.service.AnimalitosService;
import com.fermin.animalitos.service.event.NotificacionServicioAnimalitos;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
@Slf4j
//                                                  <--------- backend ------------------>
///   Usuarios navegador de internet (chrome) ----> WEB_SOCKET ----> Service de Animalitos
///   Usuarios navegador de internet (chrome) ----> 
///   Usuarios navegador de internet (chrome) ----> 
///   Usuarios navegador de internet (chrome) ----> 
///   Usuarios navegador de internet (chrome) ----> 
public class AnimalitosWebSocket extends TextWebSocketHandler{

	private final List<WebSocketSession> sesiones = new ArrayList<>();
	private final ObjectMapper mapeador = new ObjectMapper();
	private final AnimalitosService animalitosService;
	
	public AnimalitosWebSocket(AnimalitosService animalitosService) {
		this.animalitosService=animalitosService;
		animalitosService.subscribe(this::enviarNotificacion);
	}

	@Async
	private void enviarNotificacion(NotificacionServicioAnimalitos notificacion) {
		try {
			String notificacionComoTexto = mapeador.writeValueAsString(notificacion);
			sesiones.forEach(sesion -> {
				try {
					if(sesion.isOpen())
						sesion.sendMessage(new TextMessage(notificacionComoTexto));
				} catch (Exception e) {
					log.error("Error al enviar la notificacion: " + notificacionComoTexto+ " al websocket: " + sesion.toString(), e);
				}
			});
		} catch (Exception e) {
			log.error("Error al enviar serializar la notifiación: " + notificacion);
		}
	}

	@Override 
	public void afterConnectionEstablished(WebSocketSession sesion) throws Exception{
		sesiones.add(sesion);
		super.afterConnectionEstablished(sesion);
	}
	
	@Override 
	public void afterConnectionClosed(WebSocketSession sesion, CloseStatus status) throws Exception{
		sesiones.remove(sesion);
		super.afterConnectionClosed(sesion,status);
	}
	/*
	@Override
	protected void handleTextMessage(WebSocketSession sesion, TextMessage message) {
		log.info("Mensaje recibido: " + message.getPayload());
	}
	*/
	// Ni nos merece la pena... el websocket queremos que esté arrancado mientras lo está la app de Spring
    @PreDestroy
    public void preDestroy() {
		animalitosService.unsubscribe(this::enviarNotificacion);
    }

}
