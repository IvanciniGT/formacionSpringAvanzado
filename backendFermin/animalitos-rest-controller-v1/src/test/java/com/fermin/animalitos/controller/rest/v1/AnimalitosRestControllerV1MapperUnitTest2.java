package com.fermin.animalitos.controller.rest.v1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fermin.TestApplication;
import com.fermin.animalitos.controller.rest.v1.dto.DatosDeNuevoAnimalitoRestV1;
import com.fermin.animalitos.controller.rest.v1.mapper.AnimalitosRestControllerV1Mapper;
import com.fermin.animalitos.entity.TipoAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;

@ExtendWith(SpringExtension.class)				
@SpringBootTest(classes = TestApplication.class)
public class AnimalitosRestControllerV1MapperUnitTest2 {

	@MockBean
	private AnimalitosRestControllerV1Mapper mapeador;

	@Test
	@DisplayName("Probar a convertir un objeto DatosDeNuevoAnimalitoRestV1 -> DatosDeNuevoAnimalito")
	void prueba1() {
		// En este caso no tiene sentido hacer estas pruebas... igual que no ha tenido sentido hacerlo en el servicio
		DatosDeNuevoAnimalitoRestV1 origen = new DatosDeNuevoAnimalitoRestV1("Firularis", TipoAnimalito.PERRO,3);
		DatosDeNuevoAnimalito d = mapeador.toDatosNuevoAnimalito(origen);
		Assertions.assertEquals(origen.getEdad(), d.getEdad());
		Assertions.assertEquals(origen.getNombre(), d.getNombre());
		Assertions.assertEquals(origen.getTipo(), d.getTipo());
		Assertions.assertEquals("desconocido", d.getColor());
	}
	@Test
	@DisplayName("Probar a convertir un objeto DatosDeAnimalito -> DatosDeAnimalitoRestV1")
	void prueba2() {
		
	}
}
 