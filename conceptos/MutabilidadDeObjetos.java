
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
public class MutabilidadDeObjetos {

    public static void main(String[] args) {
        Productor p = new Productor();
        new Consumidor(p);
        new Consumidor(p);
        new Consumidor(p);
        new ConsumidorMalignoYPernicioso(p);
        new Consumidor(p);
        new Consumidor(p);
        p.produce();
    }

    private static class Consumidor {
        Consumidor(Productor productor) {
            productor.subscribirse(this::nuevaNotificacion);
        }

        public void nuevaNotificacion(Dato dato) {
            System.out.println("Nuevo dato: " + dato.getNumero());
        }

    }

    private static class ConsumidorMalignoYPernicioso {
        ConsumidorMalignoYPernicioso(Productor productor) {
            productor.subscribirse(this::nuevaNotificacion);
        }

        public void nuevaNotificacion(Dato dato) {
            System.out.println("Recibo dato: " + dato.getNumero());
            dato.setNumero(33);
            System.out.println("Nuevo dato que he transformado malignamente: " + dato.getNumero());
        }

    }
    private static class Dato{
        private int numero;
        public Dato(int numero){
            this.numero=numero;
        }
        public int getNumero(){
            return this.numero;
        }
        public void setNumero(int numero){ // ESTA ES LA QUE CONVIERTE EL OBJETO EN MUTABLE ... FUERA !!!!!!
            this.numero=numero;
        }
    }

    private static class Productor {
        private List<Consumer> consumidores=new ArrayList<>();;
        void subscribirse(Consumer<Dato> consumidor){
            this.consumidores.add(consumidor);
        }
        void produce(){
            Dato d = new Dato(1);
            this.consumidores.forEach(consumidor->consumidor.accept(d));
        }
    }
}