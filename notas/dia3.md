
# Servicio de Animalitos
# CRUD

altaDeAnimalito     #                           |
    persistir el animalito                      |
    enviarUnEmail NUEVO ANIMALITO !!!!!         |
recuperarTodos                                   >   Controlador REST
recuperarUno                                    |
borrarUno                                       |
modificarUno                                    |

subscripciónNotificacionesCambios               > Controlador WebSocket
    ^
    Controlador

# HTTP

Protocolo de comunicación entre cliente y servidor unidireccional
Basado en Petición- > Respuesta
          (cliente)   (servidor)
          HtpRequest  HttpResponse

Pero hay casos donde esto se queda bien pobre
    - Chat
    - Juego
    - Notificaciones
    - Subscripciones
    - Streaming
    - ...
Y quiero poder hacer PUSH desde el servidor al cliente.
HTTP está pensado para PULL, donde el cliente llama y se trae datos del servidor.

Cuando trabajamos con http, escribimos urls del tipo : http://localhost:8080/animales

# WebSocket

Protocolo de comunicación entre cliente y servidor bidireccional
No está basado en Petición- > Respuesta
Se abre una conexión por parte del cliente hacia el servidor... pero la conexión se queda abierta.
Y a partir de ahí, tanto el cliente como el servidor pueden enviar mensajes en cualquier momento.
Solo hay envío... No existe el concepto de respuesta.

Lo que configuramos son los mensajes que se envían y escucho a los que me envían.

Cuando trabajamos con websockets, escribimos urls del tipo : ws://localhost:8080/animales

Igual que podemos trabajar con https, podemos trabajar con wss (websockets seguros)

# Test-Doubles

Son objetos que usamos para pruebas y que reemplazan a los objetos reales.
Hay muchos tipos de TestDoubles:
- Dummy
- Fake
- Stub
- Mock <<< Mockito
- Spy

# Spring application.properties

Spring siempre busca por defecto al arrancar un fichero llamado application.properties (también vale application.yml)
En ese fichero Spring lee muchas propiedades que el propio framework usa para configurarse:
- BBDD a la que conectarse
- Puerto en el que escuchar
- La herramienta que voy a utilizar para autenticar a los usuarios

Pero yo puedo añadir mis propias propiedades, y luego leerlas desde mi código.

PROYECTO GLOBAL -> GIT
    Persistencia 
        SUBMODULO         Animalitos -> GIT
        SUBMODULO         Clientes -> GIT
    Servicios 
    Controladores 

Por encima de todo esto van los automatismos de CI/CD. JENKINS

Integración continua -> Entrega Continua -> Despliegue continuo
Jenkins. revisa el repo de git. Si hay un cambio (nuevo commit en dev)
Si hay un commit con una anotación de nueva versión (1.2.3-dev) -> CI
Quiero el commit en auto subido a la rama release y anotada la versión en maven (1.2.3-RC1) y le haga las pruebas
Si todo ha ido bien quiero que arranque un proyecto de Entrega Continua:
Si todo ha ido bien quiero que Jenkins suba el commit a la rama main (taggeandolo como 1.2.3)
Generará el artefacto del proyecto (.jar) -> Lo suba a un artifactory o similar de mi empresa
Y después que arraque un proceso de Despliegue continuo...
Extraeyendo ese .ja del artifactory de mi empresa y subiendolo al entorno de produción.

git add :/ && git commit -m "Esto está" && git tag 1.2.3-dev && git push origin dev --tags

main -> SIEMPRE ES PRODUCCION
release -> SIEMPRE ES PREPRODUCCION
dev ---> DESARROLLO
features -> NUEVAS FUNCIONALIDADES