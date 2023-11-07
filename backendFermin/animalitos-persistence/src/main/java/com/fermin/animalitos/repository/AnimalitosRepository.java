package com.fermin.animalitos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fermin.animalitos.entity.Animalito;
import com.fermin.animalitos.entity.TipoAnimalito;

public interface AnimalitosRepository extends JpaRepository<Animalito, Long>{

	List<Animalito> findByTipo(TipoAnimalito tipoAnimalito);
	
}
