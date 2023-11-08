#language:es

Característica: El servicio de emails

		Antecedentes:
		Dado      un servicio de emails configurado

    Esquema del escenario: Probar el envío de un email
		Dado      un destinatario: "<destinatario>"
		Y         un asunto: "<asunto>"
		Y         un cuerpo: "<cuerpo>"
		Cuando    solicito el envío de un email con los datos anteriores
		Entonces  el email se envía sin problemas
		Y         se debe recibir en la bandeja de entrada "pop" del usuario "<destinatario>" un correo
		Y         el correo debe tener por asunto: "<asunto>"
		Y         el cuerpo del correo debe contener: "<cuerpo>"

		Ejemplos:

		| destinatario                | asunto          | cuerpo                                     |
		| altas@animalitos.fermin.com | Nuevo animalito | Se ha dado de alta el animalito: Firularis |


    Esquema del escenario: Probar el envío de un email sin destinatario
		Dado      un asunto: "<asunto>"
		Y         un cuerpo: "<cuerpo>"
		Cuando    solicito el envío de un email con los datos anteriores
		Entonces  me da un error al mandar el email
		
		Ejemplos:

		| asunto          | cuerpo                                     |
		| Nuevo animalito | Se ha dado de alta el animalito: Firularis |


    Esquema del escenario: Probar el envío de un email sin asunto
		Dado      un destinatario: "<destinatario>"
		Y         un cuerpo: "<cuerpo>"
		Cuando    solicito el envío de un email con los datos anteriores
		Entonces  me da un error al mandar el email
		
		Ejemplos:

		| destinatario                | cuerpo                                     |
		| altas@animalitos.fermin.com | Se ha dado de alta el animalito: Firularis |


    Esquema del escenario: Probar el envío de un email sin cuerpo
		Dado      un destinatario: "<destinatario>"
		Y         un asunto: "<asunto>"
		Cuando    solicito el envío de un email con los datos anteriores
		Entonces  me da un error al mandar el email
		
		Ejemplos:

		| destinatario                | asunto          |
		| altas@animalitos.fermin.com | Nuevo animalito |
