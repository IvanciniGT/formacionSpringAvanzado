
# Inyección de dependencias en Spring

## Solicitar la inyección de dependencias

- @Autowired en una propiedad de una clase que fuera instanciada por Spring
- Solicitando un dato en cualquier método que sea invocado por Spring
  - Solicitando datos en el constructor de una clase que fuera instanciada por Spring

## Cómo indicar a Spring que instancia debe entregar (inyectar) cuando alguien solicite una inyección de dependencias

- @Component en una clase que cree yo extendiendo una interfaz
- @Bean en una función que creo yo dentro de una clase que anotaré con @Configuration

---

# Springboot

Es una herramienta por encima de Spring que nos permite crear aplicaciones Spring de forma más rápida y sencilla.
Nos ayuda mucho con la configuración de mi aplicación.
Aquí tenemos 2 cosas:
- Algunas anotaciones extra que facilitan la configuración de mi aplicación
- Starters: Son paquetes de dependencias preconfigurados para que mi aplicación funcione con ciertas tecnologías

# Proyecto 1: Crear un proyecto Spring Boot con un servicio rest de Animalitos Fermin: CRUD REST de Animalitos

http://miservicio/api/v1/animalitos
    v
 AnimalitosRestControllerV1 > AnimalitosService > AnimalitosRepository > BBDD
 Lógica de                    Lógica de             Lógica de              Persistencia de
 exposición del               negocio               persistencia           los Animalitos
 servicio                                                                       Lógica de datos
                                DTO:                  Entidad: Animalitos
                                    DatosAnimalito
                                    DatosDeNuevoAnimalito
                                    DatosModificablesDeAnimalito

## AnimalitosRepository

public Animalito save(Animalito animalito);
public List<Animalito> findAll();
public Optional<Animalito> findById(Long id);

## AnimalitosService

public DatosAnimalito newAnimalito(DatosDeNuevoAnimalito datosDeNuevoAnimalito); 
public DatosAnimalito modificarAnimalito(DatosModificablesDeAnimalito datosModificablesDeAnimalito); 

## AnimalitosRestControllerV1

V1? Mayor version

## Entidad

Un objeto persistible en BBDD según el estándar JPA. Es identificable. Tiene personalidad. Es un ente

## DTO

Un objeto de transporte de datos mediante el que exporto la información de mi entidad a quien le interese como me interese.

---

CADA CAPA QUE VAMOS A MONTAR VA A TRABAJAR CON SUS PROPIOS OBJETOS DE TRANSPORTE DE DATOS. REGLA BASICA:
Facilitar el mnto de la aplicación a futuro de forma exagerada.
A dia de hoy va a ser un coñazo !!!! Pero a futuro, cuando la aplicación crezca, será una bendición.

JPA: es un estándar parte de JEE que nos permite persistir objetos JAVA en BBDD.
J2EE -> JEE: Java Enterprise Edition -> JEE: Jakarta Enterprise Edition
JEE: Colección de estándares para crear aplicaciones empresariales en JAVA:
    - JDBC
    - JPA
    - JMS
    - Servlets
    - JSP
    - ...

---

App para el control de clientes. Un cliente tiene una fecha de nacimiento que quiero validar. QUIERO VALIDAR EL DNI (3) 1-8 NUMERITOS Y UNA LETRA.
LA LETRA DEBE CUADRAR CON EL NUMERO <- ES POR SER UN DNI ESPAÑOL
Qué validaciones quiero hacer?
- Formato adecuado: día/mes/año... Y que el día sea consistente con el mes y el año. 30 de febrero... 29 de febrero si el año no es bisiesto (1)
- Que sea pasada: No puedo dar de alta clientes que no hayan nacido aún.                                                                     (2)
    LOGICA DE NEGOCIO  / DATO
Me dicen como requisito que SOLO HAY UN SITIO donde puedo poner cada validación. 
Cuál sería el sitio de cada validación? IF = LOGICA que va asociada al tipo de dato.

FRONTAL                                                BACKEND
----------------------------------------------------   -----------------------------------------------------------
AngularJS                                                                       (3)
Componente WEB: FormularioPersonas  > ServicioFrontal > ControladorBackend  > Servicio Backend > Repositorio > BBDD
                (2)(1)(3)                                                     (2)(1)(3)                        (2)(1)(3) VALIDACION REAL 
                VALIDACIONES DE CORTESIA                                      VALIDACIONES DE CORTESIA          ^ INSERT SQL / PLSQL
El formato de una fecha va inherentemente asociado al tipo de dato. FECHA !

CREATE TABLE Personas (
    FechaNacimiento DATE NOT NULL
)

---

# Esquema de versionado de Software

v1.2.3

            Cuándo se incrementan esos numeritos?
- 1 MAJOR       Breaking changes. Rompo retrocompatibilidad
                    Quito funcionalidad
- 2 MINOR       Nueva funcionalidad
                Se marca una funcionalidad como obsoleta @Deprecated
                    + Pueden venir arreglos de bugs
- 3 PATCH       Arreglo de bugs


Si uso una librería en v 1.2.3 y sale la v 1.2.4 la quiero? SI
Si uso una librería en v 1.2.3 y sale la v 1.3.0 la quiero? DEPENDE... Necesito esa nueva funcionalidad o me están arreglando bugs importantes para mi?
Si uso una librería en v 1.2.3 y sale la v 2.0.0 la quiero? DEPENDE... Necesito esa nueva funcionalidad o me están arreglando bugs importantes para mi?

---
Sistema de Animalitos Fermín que va mucho más allá de el servicio Backend CRUD que estamos montando

FRONTAL                                                         BACKEND
Página web de animalitosfermin.com   v2.0.0                       Controlador REST            > Servicio          ...        > BBDD
App Android                          v2.2.0                       GET /api/v1/animalitos        recuperarAnimalitos()
App iOS                              v2.4.0                             v1.3.0                            v2.0.0
App Desktop       --->               v2.0.0                       POST /api/v1/animalitos       nuevoAnimalito()
App asistente de voz (Alexa, Siri, Google Assistant)                                                    
Sistema de voz interactivo (IVR)                                  GET /api/v2/animalitos
                                                                        v2.4.0
                                                                  POST /api/v2/animalitos

GESTION DE APIs WEB / APIGEE
                                                                                                       Animalito: name, type, age, pics
                                                                                                        v2.0.0
MAÑANA ... Y AQUI EMPIEZA LA AVENTURA!
Dicen... a un animalito hay que guardarle fotos opcionales.

Y DENTRO DE UN AÑO, como tenemos un sistema DPM y el Fermin se está gastando bien los billetes y le va como un tiro la empresa, se INTERNACIONALIZA
Los nombre en inglés

---

# Backend Fermin

AnimalitosRestControllerV1 > AnimalitosService > AnimalitosRepository > BBDD

backendFermin/
    aplicacion-backend/
        src/
            BackendFerminApplication.java <-- main
            TEST:   Smoke test: Pruebas de humo... 
                    Que he configurado bien todos los componentes de la aplicación
        pom.xml
    animalitos-rest-controller-v1/
        scr/
            class AnimalitosRestControllerV1.java
            DTO:    DatosAnimalitoRestV1.java
                    DatosDeNuevoAnimalitoRestV1.java
                    DatosModificablesDeAnimalitoRestV1.java
            Mapper: AnimalitoRestV1Mapper.java
            TEST:   Unitarios                               -> Junit
                    Integración -> Servicio-Animalitos      -> Junit
                    Sistema -> Oh yeah baby!!!!!            -> Cucumber
        pom.xml
    animalitos-service-impl/
        scr/
            class AnimalitosServiceImpl.java
            Mapeadores: AnimalitoMapper.java
            TEST:  Unitarios
                   Integración -> Persistencia
                               -> EmailsService
        pom.xml   
    animalitos-service-api/
        scr/
            interfaz AnimalitosService.java
            DTO:    DatosAnimalito.java                 POJO
                    DatosDeNuevoAnimalito.java
                    DatosModificablesDeAnimalito.java
            TEST:   Sistema
        pom.xml
    emails-service-api/
        scr/
            interfaz EmailService.java
        pom.xml
    animalitos-persistence/ -> Nos vamos a atar a un framework... No vamos a tener más de 1 implementación SPRING/JPA -> .jar
        src/
            interfaz AnimalitosRepository.java + impl... que me regala Spring
            entidad: Animalito.java
            TEST: Unitarios
        pom.xml
    pom.xml <- A través de este pom podremos gestiona todos los módulos de este proyecto, como si fueran 1
        Dependencias de los TEST !!!!!
            JUnit    \ Mockito, Spring-test
            Cucumber /
        Dependencias de Lombok
        Dependencias MapStruct

| MODULO                         | DE QUIEN DEPENDE |
| animalitos-persistence         | Spring-DATA-JPA  |
| animalitos-service-api         | DE NADIE         |
| animalitos-service-impl        | animalitos-service-api, animalitos-persistence, emails-service-api |
| animalitos-rest-controller-v1  | animalitos-service-api, spring-web  |
| application-backend            | animalitos-rest-controller-v1, animalitos-service-impl, animalitos-persistence |


Módulos de maven

JAVA 1.9 Módulos de JAVA ... module-info.java
MAVEN Módulos de maven ... pom.xml
GIT Módulos de git ... submodules
---

Spring cuando arranque una aplicación de Spring, 
Buscará: 
- Las entidades que yo haya definido en mis clases
- Los repositorios que yo haya definido en mis clases
- Los servicios que yo haya definido en mis clasesLos controladores que yo hya definido en mis clasesY creará automaticamente instancias de ellos, que poder inyectar a quién las solicite
---

En desarrollo seguimos los principios SOLID de desarrollo de software.
En testing seguimos los principios FIRST de testing de software.

F - Fast                Que vayan rapidito
I - Independent         Que un test no dependa de otros test ni de condiciones externa. Todo queda definido dentro del test -> Mantenibilidad de los test
R - Repeatable          Que el test pueda repetirlo tantas veces como quiera y siempre de el mismo resultado 
                        (CON ESTE NOS ESTA AYUDANDO AHORA EL H" - que empiezaa siempre con una BBDD limpia)
S - Self-validating     Que validen todas las cosas que deben garantizarse como resultado de una operación
T - Timely              Oportunos... en el momento adecuado.

---

Vamos a estar trabajando con lenguaje GHERKIN...
Con este lenguaje crearemos ficheros .feature
Estos ficheros .feature, escritos en lenguaje GHERKIN serán ejecutados por Cucumber (Framework de pruebas)

Gherkin realmente no es un lenguaje... es un conjunto de restricciones que imponemos sobre un lenguaje natural (uno de los que hablamos los seres humanos)
Cual? El que queramos.... Realmente el que queramos:
- Inglés
- Español
- Catalán
- Aragonés
- Asturiano