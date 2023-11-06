
# Inyección de dependencias en Spring

## Cómo solicitar una inyección de dependencias usando Spring

### Opción 1: Mediante la anotación @Autowired

```java
import org.springframework.beans.factory.annotation.Autowired;

public class MiClase {
    @Autowired
    private MiOtraClase otraClase;

    public MiClase() {
        // Aquí NO puedo usar la variable otraClase
    }
}
```

OJO: Esto tiene un truco... ésto sólo funciona si? es Spring el que crea la instancia de MiClase. 
     Si la instancia de MiClase la creamos nosotros, entonces Spring no puede inyectar la dependencia.
    Otra cosa será cómo le indico a Spring que él debe ser quien genere la instancia de MiClase... ya veremos eso!

Esto, salvo en muy, muy, muy contados casos, es una mala práctica!
Por qué? Imaginad el código que Spring va a crear por mi:
    
```java
MiClase miClase = new MiClase();
// Existe el método setOtraClase() en MiClase? NO! Entonces cómo lo hace?
//miClase.setOtraClase(new MiOtraClase()); // Aquí hace la inyección de dependencias
// La variable es pública? NO! Entonces cómo lo hace?
// miClase.otraClase = new MiOtraClase(); // Aquí hace la inyección de dependencias
// Es posible en JAVA asignar desde fuera de una clase el valor a una propiedad privada? SI... a través de un paquete que tiene JAVA llamado "java.lang.reflect"... Pero esto es muy costoso computacionalmente hablando.... y está desaconsejado. Es más, desde java 1.9, este paquete está desactivado por defecto en la máquina virtual de java.
// Además, podríamos usar la propiedad dentro del constructor?
```

### Opción 2: Solicitando el dato directamente en cualquier función que sea invocada por Spring

```java 
public class MiClase{

    public miFuncion(MiOtraClase miOtraClase){
        // Necesito una instancia de MiOtraClase
    }
}
```
OJO: Esto solo funcionará si? Si es Spring quien invoca a la función miFuncion. 
Si la invocamos nosotros, Spring no puede inyectar la dependencia.
Otro tema será cómo le indicamos a Spring que él debe ser quien invoque a la función miFuncion... ya veremos eso!

### Opción 1-2

Si tengo una clase cuyas instancias las crea Spring, y quiero que Spring inyecte una dependencia en ella, podemos hacer una mezcla de los dos métodos anteriores:

```java
public class MiClase {
    private final MiOtraClase otraClase;

    public MiClase(MiOtraClase otraClase) {
        this.otraClase=otraClase;
    }
}
```
En este caso, no se usa reflection, y además tengo disponible el dato en el constructor, por lo que puedo usarlo en el constructor.
ESTA ES LA FORMA RECOMENDADA DE HACER HOY EN DIA LA INYECCION DE DEPENDENCIAS EN SPRING


## Cómo indicar a Spring que instancia debe entregar cuando alguien solicite una inyección de dependencias

### Opción 1: Mediante la anotación @Component... o derivados(@Service, @Repository, @Controller, @RestController, @ApplicationListener)

```java
public class MiClase {
    private final MiOtraClase otraClase;

    public MiClase(MiOtraClase otraClase) {
        this.otraClase=otraClase;
    }
}

public interface MiOtraClase {
}

import org.springframework.stereotype.Component;

@Component
public class MiOtraClaseImpl implements MiOtraClase {
}
```
OJO, esto solo va a funcionar si sólo tengo una clase de la que Spring pueda crear una instancia que implemente MiOtraClase.
Si hay varias, Spring no arranca. Da error (habrá truquitos @Qualifier, @Primary, @ActiveProfiles, etc... pero eso lo veremos más adelante)

SI ESTA OPCION ME SIRVE, DEJAR DE LEER LO DE ABAJO... NO APLICA !

### Opción 2: Mediante la anotación @Bean

En ocasiones, no puedo hacer la opción 1:
Si la clase de la que quiero que se generen instancias no es mía, puedo poner arriba la anotación @Component? NO!

```java
public class MiClase {
    private final MiOtraClase otraClase;

    public MiClase(MiOtraClase otraClase) {
        this.otraClase=otraClase;
    }
}

                // Me la ofrece una librería que no está bajo mi control. No tengo capacidad de escribir @Component encima de la clase. VOY JODIDO
                    public interface MiOtraClase {
                    }

                    import org.springframework.stereotype.Component;

                    public class MiOtraClaseImpl implements MiOtraClase {
                    }

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Le indico a Spring que esta clase contiene definiciones de beans
public class MiConfiguracion {
    @Bean   // Le indico a Spring que cuando alguien solicite un objeto de tipo MiOtraClase, debe devolver lo que está función devuelva.
    public MiOtraClase miOtraClase() { // El nombre del método da igual... le podéis llamar federico! 
                                       // Lo que hace la vinculación es el tipo de dato devuelto: MiOtraClase
        return new MiOtraClaseImpl(); // Le indico a Spring que instancia debe inyectar cuando alguien solicite una instancia de MiOtraClase
    }
}
```

## NOTA:  Tanto en la opción 1, como en la 2, por defecto Spring va a crear una única instancia de la clase a inyectar.

Este comportamiento (rara vez) lo podré cambiar con la anotación @Scope

Dicho de otra forma, por defecto, las clases con anotación @Component(o derivados) se tratan por Spring como si fueran SINGLETONS
Y las funciones con anotación @Bean son invocada Una UNICA VEZ ... En este caso, Spring guarda el resultado de la función en una caché, y cuando alguien solicita una instancia de MiOtraClase, le devuelve la instancia que tiene en caché.

---

# Singleton

## Qué es un Singleton?

Es un patrón de diseño que me asegura que sólo existe una única instancia de una clase en todo el programa.

## Cómo se implementa un Singleton?

```java
public class MiClase {
    private static volatile MiClase instancia;
    // volatile: Cuando la JVM maneja una variable, cachea su valor a nivel de hilo.
    // Con esta palabra le indicamos a JVM que esa variable puede ser modificada por otro hilo, y que por tanto, no debe cachear su valor.

    private MiClase() {
    }

    public static MiClase getInstancia() {
        if (instancia == null) {                // Para evitar el synchronized una vez creada la instancia 
                                                // (que es una operación muy cara computacionalmente hablando)
            synchronized (MiClase.class) {      // Para asegurar que dos hilos no pueden entrar al if a la vez
                if (instancia == null) {        // Para que solo se llame al constructor una única vez
                    instancia = new MiClase();
                }
            }
        }
        return instancia;
    }
}
```