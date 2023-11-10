package com.fermin.animalitos.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fermin.TestApplication;
import com.fermin.animalitos.entity.Animalito;
import com.fermin.animalitos.entity.TipoAnimalito;
import com.fermin.animalitos.repository.AnimalitosRepository;
import com.fermin.animalitos.service.dto.DatosAnimalito;
import com.fermin.animalitos.service.dto.DatosDeNuevoAnimalito;
import com.fermin.emails.service.EmailsService;

@ExtendWith(SpringExtension.class)					// Que JUnit PUEDA solicitar datos a Spring
@SpringBootTest(classes = TestApplication.class)	// Arranca una app que he de tener configurada (De antemano) para que incluya mi Servicio
//@TestInstance(Lifecycle.PER_CLASS) Con esta anotación, JUnit crea una úinica instancia de la clase en la que ejecuta todas las funciones de test
// Esto es lo que necesito si quiero meter un Hook de JUNIT (Before... After...) que trabaje con datos a nivel de instancia
public class AnimalitosServiceImplUnitTest {

	private final AnimalitosService animalitosService;
	
	@MockBean
	private AnimalitosRepository animalitosRepository;
	// Se genera a través de Mockito un Mock (Realmente un test-double:dummy)
	// Cuidado... en este caso, cuantas implementaciones habría del repo de Animalitos? 2
	//  - La real que me genera Spring
	//  - La de mentirijilla que me genera Mockito
	// Esa anotación NO ES DE MOCKITO, sino de SPRING.
	// Esa anotación, además de solicitar a Mockito que cree el dummy, fuerza a que se use esa implementación en lugar de cualquier otra que hubiera configurada
	
	@MockBean
	private EmailsService emailsService;
	@Captor
	private ArgumentCaptor<String> destinatarioEmail;
	@Captor
	private ArgumentCaptor<String> asuntoEmail;
	@Captor
	private ArgumentCaptor<String> cuerpoEmail;
	@Captor
	private ArgumentCaptor<Animalito> animalito;
	
	
	AnimalitosServiceImplUnitTest(@Autowired AnimalitosService animalitosService){
		this.animalitosService=animalitosService;
	}
	
	@BeforeAll //@AfterEach @AfterAll @BeforeEach
	static void meEjecutoAntesDeCadaFuncion() {
		// Dar un comportamiento por defecto a un mock, que aplique a todos los test
	}
	

	@ParameterizedTest
	@CsvFileSource( resources = "/datos/animalitos_guays.csv", numLinesToSkip = 1)
	@DisplayName("Alta de un animalito sin nombre")
	void altaDeAnimalitoSinNombre(String nombre,String tipo, int edad ) {
		String nombreNulo = null;
		TipoAnimalito tipoAnimalito = TipoAnimalito.valueOf(tipo.toUpperCase());
		when(animalitosRepository.save(any(Animalito.class))).thenThrow(new IllegalArgumentException("Nombre vacío"));

		DatosDeNuevoAnimalito datosDeNuevoAnimalito = new DatosDeNuevoAnimalito(nombreNulo,tipoAnimalito , edad,"rojo");
		Assertions.assertThrows(Exception.class, () -> animalitosService.altaDeAnimalito(datosDeNuevoAnimalito));
	}
	@ParameterizedTest
	@CsvFileSource( resources = "/datos/animalitos_guays.csv", numLinesToSkip = 1)
	@DisplayName("Alta de un animalito con datos guays")
	void altaDeAnimalitoGuay(String nombre,String tipo, int edad ) {
		TipoAnimalito tipoAnimalito = TipoAnimalito.valueOf(tipo.toUpperCase());
		// Dado				Un servicio de Animalitos configurado √
		// Y 				Un servicio de Emails de mentirijilla, que no envia correos, pero registra las peticiones de envío 			√ SPY
		// Y 				Un repositorio de Animalitos de mentirijilla, que siempre devuelve los mismos datos que recibe con id 33 	√ STUB/FAKE
		Animalito nuevoAnimalito = Animalito.builder().id(33L).nombre(nombre).edad(edad).tipo(tipoAnimalito).build();
		when(animalitosRepository.save(any(Animalito.class))).thenReturn(nuevoAnimalito);
		//Dado				unos datos de un nuevo animalito
		//Y 				ese nuevo animalito tiene por "nombre": "<nombre>"
		//Y 				ese nuevo animalito tiene por "tipo": "<tipo>"
		//Y 				ese nuevo animalito tiene por "edad": <edad>
		DatosDeNuevoAnimalito datosDeNuevoAnimalito = new DatosDeNuevoAnimalito(nombre,tipoAnimalito , edad, "verde");
		//Cuando			se solicita al servicio de animalitos el alta de ese nuevo animalito
		DatosAnimalito datosAnimalitoDevuelto = animalitosService.altaDeAnimalito(datosDeNuevoAnimalito);
		//Entonces			se devuelve un os datos de animalito
		validarDatosAnimalito(nombre, edad, tipoAnimalito, datosAnimalitoDevuelto);
		// Asegurarme que se ha llamado al repo con los datos adecuados
		verify(animalitosRepository).save(animalito.capture());
		validarAnimalito(animalito.getValue(),null, nombre, edad, tipoAnimalito);
		// Aseguramos que se haya llamado a la función enviar Email y sacamos los datos copn los que se ha llamado:
		// Pasame los datos con los que se haya llamado a la función enviarEmail del EmailService
		validarSolicitudEmail(nombre);
	}// Montar una prueba de Integración entre el servicio de Animalitos y el repositorio de animalitos... para la función altaDeAnimalito

	private void validarSolicitudEmail(String nombre) {
		verify(emailsService).enviarEmail(destinatarioEmail.capture(), asuntoEmail.capture(), cuerpoEmail.capture());		//Y         		y se debe haber solicitado el envío de un email al usuario "altas@animalitosfermin.com"
		Assertions.assertEquals("altas@animalitosfermin.com", destinatarioEmail.getValue());		//Y         		y el correo solicitado debe tener por asunto: "Nuevo animalito"
		Assertions.assertEquals("Nuevo animalito", asuntoEmail.getValue());		//Y         		y el cuerpo del correo solicitado debe contener: "Se ha dado de alta al animalito: <nombre>"
		Assertions.assertEquals("Se ha dado de alta al animalito: "+nombre, cuerpoEmail.getValue());
	}

	private void validarDatosAnimalito(String nombre, int edad, TipoAnimalito tipoAnimalito,
		DatosAnimalito datosAnimalitoDevuelto) {
		Assertions.assertNotNull(datosAnimalitoDevuelto);   //Y 				los datos del animalito tienen por "id": 33
		Assertions.assertEquals(33, datosAnimalitoDevuelto.getId());		//Y 				los datos del animalito tienen por "nombre": "<nombre>"
		Assertions.assertEquals(nombre, datosAnimalitoDevuelto.getNombre());		//Y 				los datos del animalito tienen por "tipo": "<tipo>"
		Assertions.assertEquals(tipoAnimalito, datosAnimalitoDevuelto.getTipo());		//Y 				los datos del animalito tienen por "edad": <edad>
		Assertions.assertEquals(edad, datosAnimalitoDevuelto.getEdad());
	}
	private void validarAnimalito(Animalito animalito, Long id, String nombre, int edad, TipoAnimalito tipoAnimalito) {
		Assertions.assertNotNull(animalito);		//Y 				los datos del animalito tienen por "id": 33
		Assertions.assertEquals(id, animalito.getId());		//Y 				los datos del animalito tienen por "nombre": "<nombre>"
		Assertions.assertEquals(nombre, animalito.getNombre());		//Y 				los datos del animalito tienen por "tipo": "<tipo>"
		Assertions.assertEquals(tipoAnimalito, animalito.getTipo());		//Y 				los datos del animalito tienen por "edad": <edad>
		Assertions.assertEquals(edad, animalito.getEdad());
	}
}
 