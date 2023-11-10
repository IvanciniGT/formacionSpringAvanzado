package com.fermin.animalitos.controller.rest.v1;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fermin.animalitos.controller.rest.v1.dto.DatosAnimalitoRestV1;
import com.fermin.animalitos.controller.rest.v1.dto.DatosDeNuevoAnimalitoRestV1;
import com.fermin.animalitos.controller.rest.v1.mapper.AnimalitosRestControllerV1Mapper;
import com.fermin.animalitos.service.AnimalitosService;
import com.fermin.animalitos.service.dto.DatosAnimalito;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnimalitosWebSocket {

	private final AnimalitosService animalitosService;
	
		
}
