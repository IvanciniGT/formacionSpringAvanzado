package com.fermin.animalitos.controller.rest.v1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AnimalitosWebSocketConfigurer implements WebSocketConfigurer{

	private final AnimalitosWebSocket webSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "/animalitos/notificaciones").setAllowedOrigins("*");
	
	}
	// ws://miservicor:8080/animalitos/notificaciones

}

//CORs
// Los navegadores cuando corren JS, implementan el protocolo CORs.
// Si un JS dentro de un navegador intenta llamar a un recurso de un
// servidor distinto al servidor del que se ha descargado el JS
// el navegador pregunta a ese nuevo servidor, desde donde acepta peticiones.
// El servidor publica un listado de los origenes desde los que acepta peticiones.
// Si el origen del JS no está en el listado que publica el servidor del recurso al
// que se intenta acceder, el navegador CORTA esa comunicación.