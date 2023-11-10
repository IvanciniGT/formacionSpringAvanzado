#language:es

Característica: Servicio Rest de Animalitos V1

	Antecedentes:
		Dado        una aplicación con el servicio REST de animalitos en V1
		Y           un repositorio de animalitos

	Esquema del escenario: 	Alta de nuevo animalito con datos guays
		Dado 				Un cliente web que ataca a nuestra aplicación
		Y   				un objeto JSON
		Y 					ese objeto JSON tiene por "nombre": "<nombre>"
		Y 					ese objeto JSON tiene por "tipo": "<tipo>"
		Y 					ese objeto JSON tiene por "edad": <edad>
		Cuando      se solicita al cliente web un post del objeto JSON al endpoint "/api/v1/animalitos"
		Entonces    se devuelve una respuesta http con estado "CREATED"
		Y           en el cuerpo de la respuesta hay un objeto JSON
		Y 					ese objeto JSON tiene un campo "id" con un vaor numérico mayor que 0
		Y 					ese objeto JSON tiene un campo "nombre" con valor "<nombre>"
		Y 					ese objeto JSON tiene un campo "tipo" con valor "<tipo>"
		Y 					ese objeto JSON tiene un campo "edad" con valor <edad>
		Y 					en el repositorio de animalitos se ha guardado un animalito con el id devuelto
		Y           el animalito en el repositorio de animalitos tiene por "nombre" "<nombre>"
		Y           el animalito en el repositorio de animalitos tiene por "tipo" "<tipo>"
		Y           el animalito en el repositorio de animalitos tiene por "edad" <edad>
		#Y         	se debe recibir en la bandeja de entrada "pop" del usuario "altas@animalitosfermin.com" un correo
		#Y         	el correo debe tener por asunto: "Nuevo animalito"
		#Y         	el cuerpo del correo debe contener: "Se ha dado de alta al animalito: <nombre>"

		Ejemplos:

		| nombre   | tipo  | edad |
		| Pepito   | PERRO | 2    |
		| Firulais | GATO  | 3    |


	Esquema del escenario: 	Alta de nuevo animalito con datos ruina sin nombre
		Dado 				Un cliente web que ataca a nuestra aplicación
		Y   				un objeto JSON
		Y 					ese objeto JSON tiene por "tipo": "<tipo>"
		Y 					ese objeto JSON tiene por "edad": <edad>
		Cuando      se solicita al cliente web un post del objeto JSON al endpoint "/api/v1/animalitos"
		Entonces    se devuelve una respuesta http con estado "BAD_REQUEST"

		Ejemplos:

		| nombre   | tipo  | edad |
		| Pepito   | PERRO | 2    |
		| Firulais | GATO  | 3    |


	Esquema del escenario: 	Alta de nuevo animalito con datos ruina sin tipo
		Dado 				Un cliente web que ataca a nuestra aplicación
		Y   				un objeto JSON
		Y 					ese objeto JSON tiene por "nombre": "<nombre>"
		Y 					ese objeto JSON tiene por "edad": <edad>
		Cuando      se solicita al cliente web un post del objeto JSON al endpoint "/api/v1/animalitos"
		Entonces    se devuelve una respuesta http con estado "BAD_REQUEST"

		Ejemplos:

		| nombre   | tipo  | edad |
		| Pepito   | PERRO | 2    |
		| Firulais | GATO  | 3    |

