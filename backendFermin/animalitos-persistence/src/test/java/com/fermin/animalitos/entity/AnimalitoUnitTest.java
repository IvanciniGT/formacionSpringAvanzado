package com.fermin.animalitos.entity;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fermin.animalitos.TestApplication;
import com.fermin.animalitos.repository.AnimalitosRepository;

@ExtendWith(SpringExtension.class)
// Oye JUnit... tu vas a crear la instancia de la clase... pero... 
// hay datos que puede ser que necesites sacar de algun sitio... que otro te los suministre
// En este caso, SPRING es a quién le debes pedir esos datos
@SpringBootTest(classes = TestApplication.class)
// Oye, Spring, Arranca en pararelo mi aplicación... con las cosas que tenga configuradas
// Vamos a usar una BBDD h2 en memoria para las pruebas:
// De una vez a otra... los datos que haya creado se tiran a la basura!
class AnimalitoUnitTest {
	
	private final AnimalitosRepository repositorioDeAnimalitos;
	
	@Autowired // ES UN ESCENARIO MUY ESPECIAL... donde queremos que JUNIT pida a Spring los inyectables para que cuando JUNIT 
	           // cree la instancia los pueda suministrar
	public AnimalitoUnitTest(AnimalitosRepository repositorioDeAnimalitos) {
		this.repositorioDeAnimalitos = repositorioDeAnimalitos;
	} 
	
	//@Autowired private AnimalitosRepository respositorioDeAnimalitos;

	@Test
	@DisplayName("Probar que no puedo dar de alta un animal con nombre vacio.")
	void probarAnimalitoConNombreVacio() {
		// Contexto de ejecución // DADO
		Animalito miAnimalito = Animalito.builder().edad(3).color("rojo").tipo(TipoAnimalito.GATO).nombre("").build();
		// Lo que quiero probar (objeto de prueba: CUANDO)
		// Valor esperado de la prueba (ENTONCES)
		Assertions.assertThrows(  Exception.class, () -> repositorioDeAnimalitos.save(miAnimalito) );
	}

	@Test
	@DisplayName("Probar que no puedo dar de alta un animal sin nombre.")
	void probarAnimalitoConSinNombre() {
		// Contexto de ejecución // DADO
		Animalito miAnimalito = Animalito.builder().edad(3).color("verde").tipo(TipoAnimalito.GATO).build();
		// Lo que quiero probar (objeto de prueba: CUANDO)
		// Valor esperado de la prueba (ENTONCES)
		Assertions.assertThrows(  Exception.class, () -> repositorioDeAnimalitos.save(miAnimalito) );
	}

	@Test
	@DisplayName("Probar que no puedo dar de alta un animal sin tipo.")
	void probarAnimalitoConSinTipo() {
		// Contexto de ejecución // DADO
		Animalito miAnimalito = Animalito.builder().edad(3).color("verde").nombre("Firulais").build();
		// Lo que quiero probar (objeto de prueba: CUANDO)
		// Valor esperado de la prueba (ENTONCES)
		Assertions.assertThrows(  Exception.class, () -> repositorioDeAnimalitos.save(miAnimalito) );
	}

	@Test
	@DisplayName("Probar que no puedo dar de alta un animal sin color.")
	void probarAnimalitoConSinColor() {
		// Contexto de ejecución // DADO
		Animalito miAnimalito = Animalito.builder().edad(3).tipo(TipoAnimalito.GATO).color(null).nombre("Firulais").build();
		// Lo que quiero probar (objeto de prueba: CUANDO)
		// Valor esperado de la prueba (ENTONCES)
		Assertions.assertThrows(  Exception.class, () -> repositorioDeAnimalitos.save(miAnimalito) );
	}

	@Test
	@DisplayName("Probar que si debo poder dar de alta un animal sin edad.")
	void probarAnimalitoConSinEdad() {
		// Contexto de ejecución // DADO
		Animalito miAnimalito = Animalito.builder().tipo(TipoAnimalito.PERRO).color("verde").nombre("Firulais").build();
		// Lo que quiero probar (objeto de prueba: CUANDO)
		// Valor esperado de la prueba (ENTONCES)
		Animalito animalitoGuardado = repositorioDeAnimalitos.save(miAnimalito);
		Assertions.assertEquals(miAnimalito.getNombre(), animalitoGuardado.getNombre());
		Assertions.assertEquals(miAnimalito.getTipo(), animalitoGuardado.getTipo());
		Assertions.assertEquals(miAnimalito.getEdad(), animalitoGuardado.getEdad());
		Assertions.assertTrue(animalitoGuardado.getId() >= 0);
	}

	@Test
	@DisplayName("Probar que no puedo dar de alta un animal con nombre muy largo.")
	void probarAnimalitoConNombreLargo() {
		// Contexto de ejecución // DADO
		Animalito miAnimalito = Animalito.builder().edad(3).color("verde").tipo(TipoAnimalito.GATO).nombre("abcdefhgijklmnñopqrstuvwxyzabcdefhgijklmnñopqrstuvwxyz").build();
		// Lo que quiero probar (objeto de prueba: CUANDO)
		// Valor esperado de la prueba (ENTONCES)
		Assertions.assertThrows(  Exception.class, () -> repositorioDeAnimalitos.save(miAnimalito) );
	}

	@Test
	@DisplayName("Puedo buscar por tipo.")
	void buscarPorTipo() {
		//OPCION 1 repositorioDeAnimalitos.deleteAll();
		List<Animalito> animalitosDevuletos = repositorioDeAnimalitos.findByTipo(TipoAnimalito.PERRO);
		int iniciales = animalitosDevuletos.size();
		animalitosDevuletos.forEach( animalitoInicial -> Assertions.assertEquals(animalitoInicial.getTipo(), TipoAnimalito.PERRO));
		
		Animalito miAnimalito1 = repositorioDeAnimalitos.save(Animalito.builder().color("verde").tipo(TipoAnimalito.PERRO).nombre("Firulais").build());
		Animalito miAnimalito2 = repositorioDeAnimalitos.save(Animalito.builder().color("verde").tipo(TipoAnimalito.PERRO).nombre("Pipo").build());
		repositorioDeAnimalitos.save(Animalito.builder().tipo(TipoAnimalito.GATO).color("verde").nombre("Pancin").build());
		
		List<Animalito> animalitosDevuletos2 = repositorioDeAnimalitos.findByTipo(TipoAnimalito.PERRO);
		Assertions.assertEquals(2+iniciales, animalitosDevuletos2.size());
		Assertions.assertTrue(animalitosDevuletos2.contains(miAnimalito1));
		Assertions.assertTrue(animalitosDevuletos2.contains(miAnimalito2));

	}
}