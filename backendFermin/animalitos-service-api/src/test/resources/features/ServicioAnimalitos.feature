#language:es

Característica: Servicio de Animalitos

	Antecedentes:
		Dado        Un servicio de Animalitos configurado
		Y 					Un repositorio de Animalitos configurado

	Esquema del escenario: 	Alta de nuevo animalito con datos guays
		Dado 				Un servicio de Emails configurado
		Y   				unos datos de un nuevo animalito
		Y 					ese nuevo animalito tiene por "nombre": "<nombre>"
		Y 					ese nuevo animalito tiene por "tipo": "<tipo>"
		Y 					ese nuevo animalito tiene por "edad": <edad>
		Cuando      se solicita al servicio de animalitos el alta de ese nuevo animalito
		Entonces    se devuelven los datos del animalito
		Y 					los datos del animalito tienen un id mayor que cero
		Y 					los datos del animalito tienen por "nombre": "<nombre>"
		Y 					los datos del animalito tienen por "tipo": "<tipo>"
		Y 					los datos del animalito tienen por "edad": <edad>
		Y 					en el repositorio de animalitos se ha guardado un animalito con el id devuelto
		Y           el animalito en el repositorio de animalitos tiene por "nombre" "<nombre>"
		Y           el animalito en el repositorio de animalitos tiene por "tipo" "<tipo>"
		Y           el animalito en el repositorio de animalitos tiene por "edad" <edad>
		Y         	se debe recibir en la bandeja de entrada "pop" del usuario "altas@animalitosfermin.com" un correo
		Y         	el correo debe tener por asunto: "Nuevo animalito"
		Y         	el cuerpo del correo debe contener: "Se ha dado de alta al animalito: <nombre>"

		Ejemplos:

		| nombre | tipo  | edad |
		| Pepito | Perro | 2    |

	Esquema del escenario: 	Recuperar un animalito existente
		Dado        unos datos de un nuevo animalito
		Y 					ese nuevo animalito tiene por "nombre": "<nombre>"
		Y 					ese nuevo animalito tiene por "tipo": "<tipo>"
		Y 					ese nuevo animalito tiene por "edad": <edad>
		Y     			se solicita al servicio de animalitos el alta de ese nuevo animalito
		Y       		se devuelven los datos de animalito
		Cuando      se solicita un animalito con el id anterior al servicio de animalitos
		Entonces    se devuelven los datos del animalito
		Y       		los datos del animalito tienen por id el anterior
		Y 					los datos del animalito tienen por "nombre": "<nombre>"
		Y 					los datos del animalito tienen por "tipo": "<tipo>"
		Y 					los datos del animalito tienen por "edad": <edad>

		Ejemplos:

		| nombre   | tipo | edad |
		| Firualis | Gato | 3    |

		
	Esquema del escenario: 	Subscripciones a eventos de alta de animales
		Dado        que estoy subscrito a los eventos del servicio de animalitos
		Y 					unos datos de un nuevo animalito
		Y 					ese nuevo animalito tiene por "nombre": "<nombre>"
		Y 					ese nuevo animalito tiene por "tipo": "<tipo>"
		Y 					ese nuevo animalito tiene por "edad": <edad>
		Cuando     	se solicita al servicio de animalitos el alta de ese nuevo animalito
		Y       		se devuelven los datos de animalito
		Entonces    se recibe una notificación de un nuevo evento del servicio de animalitos
		Y        		la notificación es de tipo: "nuevo animalito" 
		Y       		la notificación es para un animalito
		Y 					ese animalito de la notificación tiene por id el anterior
		Y 					ese animalito de la notificación tiene por "nombre": "<nombre>"
		Y 					ese animalito de la notificación tiene por "edad": <edad>
		Y 					ese animalito de la notificación tiene por "tipo": "<tipo>"

		Ejemplos:

		| nombre   | tipo | edad |
		| Pipo     | Loro | 5    |
