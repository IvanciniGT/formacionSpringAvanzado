
/api/v1/animalitos

 {
    "id": 13,
    "nombre": "Firulais",
    "tipo": "PERRO",
    "edad": 23
 }


Aplicacion WEB: JSP, php, mierdas varias

JS: Componentes web
    Angular, React, Vue, Polymer

servidor con html + js

navegador  -> servidor
    <- html + js
    dentro del navegador los JS empiezan a mutar el html inicial (SPA)

W3C: World Wide Web Consortium
    - HTML
    - CSS
    - XML
    - XPATH
    - JS? NO. Hoy en día no existe un lenguaje de programación llamado JS: ECMAScript (ECMA)
    - WebComponents
    - Un componente web es una marca html propia que puedo definirme yo, con mi lógica de representación y mi lógica de negocio.
         <p> -> Parrafo <p>Hola<p>
         <a> -> Enlace  <a href="http://www.google.com">Google</a>
         <usuario id="18273482"/>
Todo navegador hoy en día soporta el que yo cree mis propias marcas personalizadas : WEB-COMPONNET
Los navegadores exportan esa funcionalidad mediante un API JS

El JS que se encarga de mutar el DOM que veo en un navegador hace peticiones a un BACKEND (REST - http, SOAP, WS)
Y extrae datos en JSON... que convierte a una representanción en pantalla HTML

Felipe (navegador chrome : JS -> WS)
Menchu (navegador firefox : JS -> WS)




```js
var variable = "hola";
variable = 3;
```
Es un lenguaje de tipado dínamico: Las variables no tienen tipo

```java 11
var variable = "hola"; // Inferencia de tipo
variable = 3; // Error de compilación

public function(String variable1){
    int numero  = 50;
    List<Map<String,List<String>>> listado = new ArrayList<Map<String,List<String>>>();
}
```
Es de tipado estático: Las variables también tienen tipo.