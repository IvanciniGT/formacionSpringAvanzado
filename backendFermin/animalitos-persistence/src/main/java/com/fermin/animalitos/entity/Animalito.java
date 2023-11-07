package com.fermin.animalitos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data		// Me da getter, setters, equals, toString
@Builder	// Me da un patron builder para crear instancias
@Entity		// Quiero que esta clase sea persistible en BBDD
			// Lógica de persistencia (estructura de almacenamiento)
@Table(name = "animalitos")
@NoArgsConstructor
@AllArgsConstructor
public class Animalito {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false, length=50)
	@NotBlank(message="El nombre de un animalito no puede estar vacío")
	private String nombre;
	
	@Column(nullable=false)
	private TipoAnimalito tipo;

	private Integer edad;
	
}

