import java.util.function.*;
// Function<A,B>    Representa una función que recibe un parámetro de tipo A y devuelve un dato de tipo B
// Consumer<A>      Representa una función que recibe un parámetro de tipo A y no devuelve nada
// Supplier<B>      Representa una función que no recibe parámetros y devuelve un dato de tipo B
// Predicate<A>     Representa una función que recibe un parámetro de tipo A y devuelve un booleano

public class ProgramacionFuncional {

    public static void main(String[] args) {

        String texto = "hola";      // Statement: Declaración, Enunciado (Frase u Oración)
        /*
            "hola"          Colocar un objeto de tipo String en memoria RAM con el valor "hola"
            String texto    Creamos una variable que puede apuntar a datos de tipo String
            =               Asigna la variable al valor.
         */

         texto = "adios";
        /*
            "adios"         Colocar un objeto de tipo String en memoria RAM con el valor "adios"
                             En otro sitio distinto.. En este punto, tengo 2 objetos de tipo String en memoria "hola", "adios"
            texto           desasigna la variable de su valor anterior
            =               Asigna la variable al valor nuevo
                             El objeto(dato) "hola" se convierte en basura... 
                             que en un momento dado será o no eliminada por el Garbage Collector
         */
        System.out.println(doblar(10));

        Function<Integer, Integer> miFuncion = ProgramacionFuncional::doblar; // JAVA 8 Operador ::, me permite referenciar funciones
        System.out.println(miFuncion.apply(15));

        imprimirSaludo(ProgramacionFuncional::generarSaludoInformal, "Juan");
        imprimirSaludo(ProgramacionFuncional::generarSaludoFormal, "Federico");
        // Otro operador que aparece en Java 1.8 es el operador lambda -> , que permite definir expresiones lambda.
        // Cosa que tenemos en C#, python o JS desde tiempos inmemoriales.... y que en java les costó un poco más
        // Una expresión lambda es trozo de código que devuelve una función anónima creada dentro del statement en tiempo de ejecución.
        int numero = 5+6;   // Otro statement
                     ///  Es una expresión: Un trozo de código que devuelve un valor

        Function<Integer, Integer> miFuncion2 = (Integer numero2) -> {
                                                                    return numero2*3;
                                                                };

        Function<Integer, Integer> miFuncion3 = (numero2) -> { // Inferencia de tipos
                                                                    return numero2*3;
                                                                };

        Function<Integer, Integer> miFuncion4 = numero2 -> {
                                                                    return numero2*3;
                                                                };        
        System.out.println(miFuncion2.apply(7));

        Function<Integer, Integer> miFuncion5 = numero2 -> numero2*3;

        System.out.println(miFuncion5.apply(8));
        imprimirSaludo(pepito -> "Bienvenido Mr. "+pepito, "Marshall");

    }

    public static int doblar(int numero) {
        return numero*2;
    }

    public static int triple(int numero) {
        return numero*3;
    }
    public static String generarSaludoInformal(String nombre) {
        return "Hola " + nombre;
    }

    public static String generarSaludoFormal(String nombre) {
        return "Buenos días " + nombre;
    }

    public static void imprimirSaludo(Function<String, String> generadorDeSaludo, String nombre) {
        System.out.println(generadorDeSaludo.apply(nombre));
    }

}