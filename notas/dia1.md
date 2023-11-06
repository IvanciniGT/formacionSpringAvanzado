
# Framework

Conjunto de librerías... que define una metodología/forma de trabajo (forma de usar esas librerías a la hora de construir una aplicación).

# Spring

Framework (más de 200 librerías) JAVA que ofrece inversión de control.
Por ello:
- Spring se encargará de aportar el flujo de la aplicación.
- Además, me va a permitir hablar usando un paradigma de programación declarativo. Tiene su propia SEMANTICA
- Y me facilita la inyección de dependencias, para respetar el principio de inversión de dependencias.

# Inversión de Control

Es otro patrón de desarrollo de software mediante el cual, el programador deja de escribir el flujo de la aplicación... dejando esa 
responsabilidad al framework de Inversión de Control.

Frameworks de Inversión de Control: Spring, Angular

---

# Principios SOLID

Son 5 principios recopilados por Robert C. Martin (Uncle Bob) que nos ayudan a construir software mantenible y extensible.
Seguir estos principios nos ayuda a montar sistemas con componentes DESACOPLADOS (con un nivel de acoplamiento bajo).

S - Single Responsibility Principle (SRP)

    Un componente de mi sistema debería tener una única responsabilidad... y por tanto un único motivo para cambiar.

O - Open/Closed Principle (OCP)

    Un componente de mi sistema debería estar abierto a la extensión pero cerrado a la modificación.

L - Liskov Substitution Principle (LSP)

    Si tengo un componente de tipo A, debería poder sustituirlo por otro componente de tipo B, sin que el comportamiento de mi sistema cambie.

I - Interface Segregation Principle (ISP)

    Muchas interfaces específicas son mejores que una interfaz general.

D - Dependency Inversion Principle (DIP) ** Posiblemente el más importante **

    Los módulos de alto nivel no deberían depender de implementaciones de los módulos de bajo nivel. Ambos deberían depender de abstracciones (interfaces, clases abstractas, pojo -> DTO): DE UN API.

# Inyección de dependencias

Es un patrón de desarrollo que nos permite respetar el principio de inversión de dependencias.

Al aplicar este patrón, hacemos que las clases que necesitan instancias de otras clases para funcionar, no las instancien ellas mismas, 
sino que se las suministradas desde fuera.

Claro que... lo único que he hecho es mover el problema a otro sitio... 
¿Quién instancia las clases que necesito? SPRINT, por ser un Contenedor de Inversión de Control.

ES CLAVE además para hacer pruebas unitarias / de integración.

---

# Ejemplo de aplicación

Aplicación de consola que permita consultar palabras en un diccionario de un determinado idioma. 
La aplicación mostrará si la palabra existe o no... 
Caso que exista mostrará las definiciones de la misma.
Caso que no exista mostrará las palabras más cercanas (similares) a la palabra buscada.

Front-consola + API + Back-palabras.. idiomas... definiciones

# API - librería para gestionar diccionarios. -> diccionario-api-v1.0.0.jar

    Conjunto de Interfaces, Clases(muy básicas-POJO, Enum...) que nos permiten comunicarnos con una librería.

    package com.diccionario.api;

        public interface Diccionario {
            boolean existe(String palabra);
            //List<String> getDefiniciones(String palabra);  // MIERDA GIGANTE !!!! El Sonar os la tira para atrás.
                // Archilolongo: En ESPAÑOL no existe
                    - null              \ - Null tiene una ventaja: Permite discriminar entre dos casos: La palabra existe o no.
                                         \                                                               Si la función ha realizado su cometido
                                          \                                                              o no
                    - lista vacía         / El comportamiento de la función no es explito. Al ver la signatura no sabemos que puede devolver.
                    - NoSuchWordException - Tiene una ventaja frente a la lista vacía: El comportamiento de la función es explícito en la
                                            signatura.
                                          Por contra... sería una muy mala práctica usar una exception para controlar el flujo de la aplicación.
                                          Generar una exception es muy costoso en tiempo de ejecución. Lo primero que es necesario hacer:
                                          es un volcado de la pila de ejecución (stacktrace). Esto es muy costoso.
                // Desde Java 1.8, solo habría una opción válida:
            Optional<List<String>> getDefiniciones(String palabra);
        }

        public interface SuministradorDeDiccionarios{
            boolean tienesDiccionarioDe(String idioma);
            Optional<Diccionario> getDiccionario(String idioma);
        }

# Implementación de la librería de diccionarios... Palabras estén guardadas en bbdd.... Palabras estén en ficheros... webservice
## Implementación que trabaja con ficheros: diccionario-ficheros-v1.0.0.jar

    package com.diccionario.impl.ficheros;

        public class SuministradorDeDiccionariosDesdeFichero implements SuministradorDeDiccionarios {

            public SuministradorDeDiccionariosDesdeFichero() { ... }

            @Override
            public boolean tienesDiccionarioDe(String idioma) { ... }
         }

        public class DiccionarioDesdeFichero implements Diccionario {

            public DiccionarioDesdeFichero() { ... }

            @Override
            public boolean existe(String palabra) { ... }

            @Override
            public Optional<List<String>> getDefiniciones(String palabra) { ... }
        }

# Aplicación de consola

    package com.aplicacion;

    import com.diccionario.api.Diccionario;                                     // Interface
    import com.diccionario.api.SuministradorDeDiccionarios;                     // Interface         
    //import com.diccionario.impl.ficheros.SuministradorDeDiccionariosDesdeFichero;   // Y AQUI LA ACABO DE REGAR ENTERA !!! IMPLEMENTACION
                                                                                    // ESTA ES LA MUERTE DEL PROYECTO 
            // ME ACABO DE CAGAR EN EL PRINCIPIO DE INVERSIÓN DE DEPENDENCIAS
            // Los módulos de alto nivel(app) no deberían depender de implementaciones de los módulos de bajo nivel (diccionarios-api)
    public class App {

        private final SuministradorDeDiccionarios miSuministradorDeDiccionarios;
        public App(SuministradorDeDiccionarios miSuministradorDeDiccionarios) {  //Inyección de dependencias
            this.miSuministradorDeDiccionarios = miSuministradorDeDiccionarios;
        }

        //...
        public void procesarPeticion(String idioma, String palabra/*, SuministradorDeDiccionarios miSuministradorDeDiccionarios*/) { //Inyección de dependencias
            // ...
            boolean existe = false;
            //SuministradorDeDiccionarios miSuministradorDeDiccionarios = new SuministradorDeDiccionariosDesdeFichero();
            if(miSuministradorDeDiccionarios.tienesDiccionarioDe(idioma)){
                Diccionario miDiccionario = miSuministradorDeDiccionarios.getDiccionario(idioma).get();
                existe = miDiccionario.existe(palabra);
            }
            //...
        }

    }

    // new App(suministrador); Este trozo de código no lo voy a escribir yo... Que lo escriba Spring, cuando lo considere necesario.
    // Y entonces que se de cuenta de que hace falta un SuministradorDeDiccionarios... y que busque cuál hay disponible en el classpath
    // Cree una instancia de ese SuministradorDeDiccionarios... y me la pase a mi App.
    // Spring, por ser un Contenedor de Inversión de Control, es capaz de hacer esto.
    // Es decir, me va a facilitar el uso de un patrón de Inyección de Dependencias, para que mi aplicación pueda respetar el principio de Inversión de Dependencias.

    //  App -> diccionario-ficheros-v1.0.0.jar -> diccionario-api-v1.0.0.jar              (ESTO SE REFLEJA EN MAVEN)
    //   |                                                      ^
    //   +------------------------------------------------------+


    //  App -> diccionario-api-v1.0.0.jar <- diccionario-ficheros-v1.0.0.jar   ESTO ES GUAY !!!!

---

Optional posiblesSignificados = miDiccionario.getSignificados("casa");
if(posiblesSignificados.isPresent()) {  // Si me llega dato lo saco
    List<String> significados = posiblesSignificados.get();
    // Mostrar significados
} else {
    // Mostrar palabras similares
}

List<String> significados = miDiccionario.getSignificados("casa");
if(significados != null) {  
    // Mostrar significados
} else {
    // Mostrar palabras similares
}

List<String> significados = miDiccionario.getSignificados("casa");
if(significados.size() > 0) {  
    // Mostrar significados
} else {
    // Mostrar palabras similares
}

---

# Ejemplo de proyecto 2:

Montar una ETL(extraer, transformar, cargar).. lo usamos mucho para alimentar datawarehouses.
- Cada noche lea clientes de un fichero csv
- Que los cargue en una tabla de una bbdd MariaDB <- CLASE que defina la lógica de cómo cargar una persona en una BBDD
- Ah.. y que cuando empiece mande un email avisando
- Ah... y que valide el DNI del cliente
- Ah... y que cuando acabe mande un email avisando
- Ah... y que si falla mande un email avisando
- Ah..  y que revise que el cliente tiene todos los campos informados.
- Ah... y que mire si el cliente es mayor de edad 
    - Si no lo es que mande un correo

ESTO SON LOS REQUISITOS, expresados en lenguaje declarativo.
Doy flujo? NO. Doy ordenes? NO. Doy instrucciones? NO. 
Digo lo que quiero tener. PUNTO PELOTA... y que el framework se encargue de hacerlo (el framework es el que va a aportar el flujo) 

ESTO ES INVERSION DE CONTROL

---

Programa: Algoritmo / FLUJO de mi programa
1- Mandar email de inicio
2- Leer fichero csv
3- Para cada cliente: BUCLE: FOR-WHILE
    3.1- Validar campos obligatorios -> Se descarta el cliente
    3.2- Validar DNI -> Se descarta el cliente
    3.3- Validar edad -> Si (IF) es menor de edad mandar email
    4-   Cargar cliente en bbdd
5- Mandar email de fin

---

# Paradigmas de programación

Una forma de transmitir la información (órdenes) a una computadora.
Esto no es algo específico de los lenguajes de programación, sino que es algo que se aplica a cualquier
lenguaje (incluidos los lenguajes naturales, los que hablamos los seres humanos)

    FELIPE SI NO HAY VENTANA Agarra un mazo y agujero al canto!
    FELIPE, IF hay algo que no sea una silla debajo de la ventana, la quitas
    FELIPE, IF no hay ya una silla debajo de la ventana, 
            FELIPE, pon una silla debajo de la ventana          Imperativo (ORDEN)
        ELSE
            FELIPE, SI NO HAY SILLAS, vete al ikea por una silla
    El problema del lenguaje imperativo es que me centro en lo que debe hacer 
    Felipe... y he perdido de vista lo que realmente es importante, que es "que debajo de la ventana debe haber una silla": LO QUE QUIERO CONSEGUIR

    FELIPE, debajo de la ventana debe haber una silla   Declarativo (AFIRMACIÓN)
        A quién le acabo de largar el muerto? A Felipe
    La gracia del lenguaje declarativo es que me centro en lo que realmente es importante, que es "que debajo de la ventana debe haber una silla": LO QUE QUIERO CONSEGUIR.. Delegando la responsabilidad de cómo conseguirlo en Felipe.

    Las herramientas de software que a día de hoy triunfan son las que permiten programación declarativa:
    - Kubernetes
    - Terraform
    - Ansible
    - Spring
    - Angular

- Imperativo            Cuando indico órdenes que deben ejecutarse secuencialmente
                        A veces necesito romper esa secuencialidad (if, for, while, switch, break, continue, return)
                        ODIAMOS LA PROGRAMACION IMPERATIVA.... LA DETESTAMOS.. solo que estamos muy acostumbrados a ella!
                        Pero es horrible. Es muy difícil de mantener. Es muy difícil de extender. Es muy difícil de reutilizar.
- Procedural            Cuando el lenguaje me permite definir mis propias funciones/métodos/procedimientos/subrutinas
                        Y ejecutarlos posteriormente
- Funcional             Cuando el lenguaje me permite referenciar una función/procedimiento/método desde una variable
                        Y posteriormente ejecutar esa función/procedimiento/método desde esa variable                -> Desde java 1.8
                        Necesitamos ponernos las pilas un huevo con programación funcional. TODO EL API DE JAVA Está migrando a programación funcional.
                        Estamos en Java 21.
                        El punto, no es lo que significa programación funcional... sino lo que puedo empezazr a hacer, una vez que dispongo de programación funcional
                            - Puedo crear funciones que reciban funciones como parámetros
                            - Puedo crear funciones que devuelvan funciones
                        Y aquí es cuando la cabeza estalla 
- Orientado a Objetos   Cuando el lenguaje me permite definir mis propios tipos de datos, con sus props y sus métodos.
                        Y crear instancias de esos tipos de datos.

                        Tipos de datos:     Propiedades:                Métodos:
                            String              secuencia de caracteres     length(), toUpperCase(), toLowerCase(), indexOf(), substring(), ...
                            List                secuencia de elementos      size(), add(), remove(), get(), ...  
                            LocalDate           dia, mes, año               plusDays(), plusMonths(), plusYears(), minusDays(), minusMonths(), minusYears(), ...
                            Persona             nombre, dni, edad           validarDNI(), esMayorDeEdad(), ...
- Declarativo

--- 

# Pruebas de Software

## Vocabulario en el mundo del testing

- ERROR     Los humanos cometemos errores (por estar cansados, faltos de concentración o conocimiento)
- DEFECTO   Al cometer un ERROR, los humanos introducimos DEFECTOS (BUGs) en el software
- FALLO     Un defecto(bug) puede manifestarse en tiempo de ejecución como un FALLO (desviación del comportamiento esperado)

## Para qué sirven las pruebas?

- Para asegurar el cumplimiento de unos requisitos
- Para identificar la mayor cantidad posible de FALLOS en el software antes del despliegue en producción.
  - Una vez identificado un FALLO, debemos arreglar el DEFECTO que lo ha provocado.
    Pero, para arreglarlo hemos de identificarlo primero (Depuración o debugging)
- Para proveer la mayor cantidad posible de información que permita la rápida identificación y corrección de los DEFECTOS, desde los FALLOS que se produzcan en ejecución. **1
- Para identificar DEFECTOS:
  - Revisión del producto / código: SonarQube (que hace lo que antes hacía un desarrollador senior)
  - Revisión de requisitos
  - Revisión de un modelo de datos
- Haciendo un análisis de causas raíces, podemos identificar los ERRORES que se están cometiendo... y tomar acciones preventivas, que eviten errores en el futuro.
- Para saber cómo va mi proyecto
- Para ayudarme en el proceso de desarrollo: Test-First TDD, BDD, ATDD
  - Test first: Primero diseño las pruebas y luego escribo el código que las hace pasar
    - Es lo normal (habitual) en cualquier industria... ya era hora de que lo empezáramos a hacer en el desarrollo de software
  - TDD: Test First + Refactorización en cada ciclo.
- ...
  
## Tipos de pruebas

Cualquier prueba, del tipo que sea, se debe centrar en una única característica del sistema/componente que se está probando **1

Las pruebas las clasificamos usando diferentes taxonomías (paralelas entre si):

### En base al objeto de prueba

Pruebas funcionales:            Se centran en la funcionabilidad del software
Pruebas no funcionales:
    - Rendimiento
    - Carga
    - Estres
    - Seguridad
    - UX
    - HA

### En base al nivel de la prueba (scope)

- Unitarias             Se centra en una única característica de un componente AISLADO de mi sistema

            TREN: Motor, Ruedas, Sistema de frenos, Sillas, Ventanas, Puertas, ...

                MOTOR       Le meto corriente y veo si gira
                SILLAS      Carga, UX, Seguridad

                RUEDAS:      Las pongo en una estructura que monto de mentirijilla, con un eje... y le pego un viaje con la mano... a ver si gira

                SISTEMA DE FRENO: Le meto corriente y veo si las pinzas cierran o no cierran.

            MICROSERVICIO:  CRUD Animalitos - Tienda online de Animalitos Fermin!

                @RestController     @Service            @Repository
                Controlador     >   Servicio        >   Repositorio 
                  Lógica de           Lógica de           Lógica de 
                  exposición          negocio             persistencia
                  del servicio

                                AnimalitosService
                                    Animalito altaDeAnimalito(DatosDeNuevoAnimalito animalito) {
                                        // Validaciones
                                        // Persistencia -> AnimalitosRepository
                                        // Lógica de negocio
                                        // Mandar un email: ailtas@animalitos.fermin.com
                                            -> EmailsService
                                    }

                    AnimalitosService   -> AnimalitosRepository
                                        -> EmailsService

                    Quiero probar la función: altaDeAnimalito
                    - Qué tipo de prueba es? Depende... del contexto en que haga la prueba
                      - Si hago la prueba trabajando con el EmailsService real... y con el AnimalitosRepository real... es una prueba de sistema
                      - Si hago la prueba aislando al componente del EmailsService y del AnimalitosRepository reales... es una prueba unitaria
                      - Si hago la prueba aislando al componente del EmailsService, pero trabajando con el AnimalitosRepository real... es una prueba de integración entre el AnimalitosService y el AnimalitosRepository
                      - Si hago la prueba aislando al componente del AnimalitosRepository, pero trabajando con el EmailsService real... es una prueba de integración entre el AnimalitosService y el EmailsService

            El problema es cómo aislo al componente del resto de componentes de mi sistema: Test-Doubles (Martin Fowler)
                - Mocks <- Mockito
                - Stubs
                - Fakes
                - Dummies
                - Spies

- Integración           Se centra en la COMUNICACION de 2 componentes

                TREN... 
                    SISTEMA DE FRENOS ---> RUEDAS
                    ^                         ^
                    Meto corriente          Se para

- End2End/Sistema       Se centran en el COMPORTAMIENTO del sistema en su conjunto

                TREN... 
                    MOTOR ---> RUEDAS ---> SISTEMA DE FRENOS ---> SILLAS ---> VENTANAS ---> PUERTAS
                Y le doy al botón de start en el tren... y va pa'tras!

  - Aceptación          Las que me piden... que habitualmente son un subconjunto de las anteriores

### En base a la forma de ejecución

- Dinámicas             Se ejecutan ejecutando el código        >   FALLOS
- Estáticas             Se ejecutan sin ejecutar el código      >   DEFECTOS (sonar, revisión de requisitos)

### En base al conocimiento del objeto de prueba

- Caja blanca           Se conoce el código fuente
- Caja negra            No se conoce el código fuente

### Otros conceptos

- De regresión          Cualquier prueba que VUELVO a ejecutar para ver que no he roto nada


---

# Metodologías ágiles

Es una metodología de GESTIÓN de proyectos de software: SCRUM, EXTREME PROGRAMMING, KANBAN, LEAN, CRYSTAL, FDD, DSDM, ...
Es una forma de gestionar proyectos diferente a la que usábamos antes: Metodologías en cascada (waterfall, V, espiral...), hoy en día conocidas como metodologías clásicas / tradicionales.
Salen para evitar los problemas que teníamos los desarrolladores en el día a día de nuestros proyectos al usar las metodologías clásicas.

- La característica clave es entregar el producto de forma incremental... para obtener un rápido feedback de los usuarios.
    Dia 30 desde el inicio del proyecto hacer la primera instalación en producción de mi software.
        Una instalación 100% funcional!
        A lo mejor solo con el 10% de la funcionalidad... pero 100% funcional.
        Qué pruebo aquí? El 10% de la funcionalidad.
    Dia 50 Instalación 2 +5% de funcionalidad
        Qué pruebo aquí? El 5% +10% de la funcionalidad.
    Dia 70 Instalación 3 +15% de funcionalidad
    Dia 90 Instalación 4 +10% de funcionalidad

Las met. ágiles resuelven muchos de los problemas que teníamos con las met. clásicas... pero han venido a traer otros nuevos:
- Cuantas veces tengo que hacer ahora pruebas?
Las pruebas con las met. ágiles se multiplican
... igual que las instalaciones en producción y en pre

Y de donde sale la pasta? y los recursos? y el tiempo? NO LO HAY... no es asumible.
Y a esto solo le encuentro 2 soluciones:
1- Paso de pruebas... y me la juego... EN SERIO ???!!??
2- AUTOMATIZAR LAS PRUEBAS, LAS INSTALACIONES EN PRE Y EN PRODUCCIÓN


> El software funcionando es la MEDIDA principal de progreso.

La principal(la que hay que usar) MEDIDA de progreso (cómo va mi proyecto) es "El software funcionando" > INDICADOR para saber qué tal va el proyecto.

"Software funcionando": Un software que funciona.
Quién dice que el software funciona? Las pruebas.





# DEV--->OPS

Devops es una cultura, una filosofía, un movimiento en pro de la AUTOMATIZACION. AUTOMATIZACION de qué? DE todo lo que hy entre el desarrollo de software y la puesta en producción del mismo.

Devops no es un perfil: Es el tio que maneja el Jenkins o similar (Travis, Github actions, Gitlab CI/CD, Bamboo, TeamCity).

No es posible ir a una met. ágil sin abrazar una cultura devops.

---

Cualquier aplicación de Spring, va a tener un método main... por se r una app JAVA.

Todos los métodos main de cualquier app Spring son iguales:

```java
public class MiApp{
    public static void main(String[] args){
        SpringApplication.run(MiApp.class, args); // Inversión de control
    }
}
```

---

Spring me da un conjunto de anotaciones para hacer la inyección de dependencias:
- @Component
- @Bean
- @Configuration
- @Autowired
- @Scope
- @Lazy
- @Primary
- @Profile
- @ActiveProfiles
- @Qualifier

Pero además, las distintas librerías de Spring me dan otras anotaciones, que añaden funcionalidad o la semántica a las anteriores.
- @Service...    Un service es un @Component que contiene lógica de negocio (realmente podría poner dentro lo que me diera la gana)
- @Repository... Un repository es un @Component que contiene lógica de persistencia (realmente podría poner dentro lo que me diera la gana)
- @Controller... Un controller es un @Component que contiene lógica de exposición de un servicio (realmente podría poner dentro lo que me diera la gana)
- @RestController... Un restcontroller es un @Controller que expone servicios mediante REST (realmente podría poner dentro lo que me diera la gana)
  - @Mapping        Cómo mapear endpoints http a funciones de mi código
  - @GetMapping     Puedo hacer mapeos de peticiones que llegan a mi servidor con método GET de http
  - @PostMapping    Puedo hacer mapeos de peticiones que llegan a mi servidor con método POST de http

Imaginaros que tengo una clase que contiene lógica de negocio y de persistencia. Qué anotación le pongo? @Component? @Service? @Repository?
    @Component: I
    @Service y @Repository: I

ESTA COMO EL CULO planteao... ME ACABO DE CAGAR EN EL PRINCIPIO DE RESPONSABILIDAD ÚNICA (SRP)

Tengo un sistema diseñado fatal !

---

# MAVEN

Una herramienta de automatización de tareas habituales de mi proyecto:

- compile
- test-compile
- test
- package
- envío de mi código a sonar
- generación de una imagen de contenedor docker
- La generación del javadoc
- La generación de un informe de cobertura (JACOCO) para su posterior envío a sonar

Y para algunos de ellos, mi proyecto necesita dependencias...bien... también me las gestiona maven.

Maven nos ofrece (es configurable aunque nunca lo cambiamos) una estructura de carpetas para organizar nuestro proyecto.

proyecto/
    src/
        main/
            java/
            resources/
        test/
            java/
            resources/
    pom.xml

pom.xml es el archivo de configuración de maven para mi proyecto:
    - Los goals que puedo ejecutar con maven. GOALS: Esas tareas que quiero automatizar
    - Los goals son ejecutados mediante plugins. PLUGINS: Son los que realmente hacen el trabajo
    - Por defecto cualquier proyecto maven vienen con una serie de plugins preinstalados... aunque yo nos defina explícitamente en mi pom.xml
      - maven-compiler-plugin
      - maven-surefire-plugin
      - maven-jar-plugin
      - maven-resources-plugin
    - Además, definiremos en este archivo: Coordenadas de mi proyecto (groupId, artifactId, version) y otros metadatos (nombre, descripción , licencia, website...)
    - Y las dependencias de mi proyecto para cada uno de los goals

Entre los goals por defecto de maven, tenemos:
- compile       Compila los archivos de src/main/java y los deja en target/classes
                Además copia los archivos que están en src/main/resources a target/classes
- test-compile  Compila los archivos de src/test/java y los deja en target/test-classes
                Además copia los archivos que están en src/test/resources a target/test-classes
- test          Ejecuta las pruebas que tenga definidas en target/test-classes
                Esas pruebas son ejecutadas por el plugin maven-surefire-plugin, que por defecto viene configurado para detectar pruebas JUnit
- package       Empaqueta mi app
        Si es un jar: Empaqueta los archivos de target/classes y los deja en target/nombre-de-mi-app.jar
        Si es un war.... Esto está ya muy obsoleto... pero empaqueta lo que tengo en target/web y lo deja en target/nombre-de-mi-app.war
- install       Copia mi archivo .jar a mi carpeta .m2 (mi repo de dependencias local en mi carpeta de usuario)
                Para que esté disponible en otros proyectos que realice en mi máquina 
- clean         Borra la carpeta target

---

Lombok:
Nos ofrece anotaciones que nos hacen la vida más fácil:
@Getter     Me genera en auto. getter: De todas las props privadas de mi clase (si pongo la anotación a nivel de clase)
            o de las props privadas que yo le indique (si pongo la anotación a nivel de prop)
@Setter     Me genera en auto. setter: De todas las props privadas de mi clase (si pongo la anotación a nivel de clase)
            o de las props privadas que yo le indique (si pongo la anotación a nivel de prop)
@AllArgsConstructor
@NoConstructor
@RequiredArgsConstructor    Solo para props final
@EqualsAndHashCode          Me genera Equals
@ToString                   Me genera un método toString decente
@Builder                    Me genera un builder para mi clase
@Data = 
    @Getter
    @Setter
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString

Los getter y los setter son una cagada descomunal que tenemos en la sintaxis de JAVA.

```java
public class Usuario{
    public String nombre;
    public String apellidos;
    private int edad;
    public int getEdad(){
        return edad;
    }
    public void setEdad(int edad){
        if(edad < 0){
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        this.edad = edad;
    }

}
...
Usuario u1 = new Usuario();
u1.nombre = "Pepe";
u1.apellidos = "Perez";
u1.edad = 25;
System.out.println(u1.nombre);
System.out.println(u1.apellidos);
System.out.println(u1.edad);
// ESTO ESTA CATALOGADO COMO MUY MALA PRACTICA EN JAVA... Y es cierto. por qué? Para encapsular los datos... ROLLO BANANERO !!!
// Esto me vale SOLO para facilitar el mnto de la app en el futuro. 
El dia 1, escribo la clase Usuario como está arriba
Del día 2 al día 100 tengo un montón de pringaos usando mi clase... como tengo aquí arriba.
El dia 101 se me ocurre la brillante idea de querer validar si la edad es positiva... Si no lanzo exception.
El día 102 estoy muerto... Tengo al montón de pringaos buscándome con un Kalashnikov. 
Por si acaso el día de mañana quiero meter una validación, devolver algo diferente en un getter... dejo hoy creado los getter y los setter.
```


```java
public class Usuario{
    private String nombre;
    private String apellidos;
    private int edad;
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getApellidos(){
        return apellidos;
    }
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }
    public int getEdad(){
        return edad;
    }
    public void setEdad(int edad){
        this.edad = edad;
    }
}
...
Usuario u1 = new Usuario();
u1.setNombre("Pepe");
u1.setApellidos("Perez");
u1.setEdad(25);
System.out.println(u1.getNombre());
System.out.println(u1.getApellidos());
System.out.println(u1.getEdad());
```

En cualquier otro lenguaje de programación: PYTHON, JS, C# tengo el concepto de property, que me dan una alternativa a esto... para poder hacer cambios en el futuro sin joder a la gente que hayan estado usando mi clase... y sin necesidad de preveer el futuro.

```java
public class Usuario{
    @Getter @Setter private String nombre;
    @Getter @Setter private String apellidos;
    @Getter @Setter private int edad;
}

@Getter @Setter 
public class Usuario{
    private String nombre;
    private String apellidos;
    private int edad;
}
```
---
Viene de serie con la librería de Spring: spring-boot-starter-test
JUNIT: Framework de pruebas de todo tipo
MOCKITO: Framework de test-doubles