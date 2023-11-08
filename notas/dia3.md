
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
