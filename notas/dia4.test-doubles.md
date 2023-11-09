# Test doubles

Son objetos que usamos cuando creamos pruebas unitarias y de integración.
Reemplazan a los componentes reales que tiene mi sistema.

Me ayudan a: 
- Aislar el código que quiero probar, centrándome en él y no en sus dependencias.
- Poder probar el código que quiero probar, aunque sus dependencias no estén disponibles.
- Me ayudan a que los test se ejecuten más rápido, porque no dependen de componentes externos.

El problema viene cuando tengo componentes que dependen de otros componentes.

    ComponenteA ---(1)---> ComponenteB
                <--(2)----

    Eso en la práctica significa que en el componente A tengo una función que llama a una función del componente B.
    En esa función, como en todas tengo una llamada y una respuesta (o no).

Aislar al componente A del componente B, en la práctica significa que:
- Si hago una prueba y hay un fallo, quiero asegurarme de que el fallo no está siendo provocado por B,
- de forma que si la prueba falla, tenga certeza de que el fallo está en A.

Lo que hacemos es montar un Reemplazo con comportamiento controlado de l componente B -> TEST DOUBLE

En ocasiones sólo me interesa controlar la respuesta que devuelve el componente B, con independencia de los argumentos que reciba (2): STUB

    Para probar la función recuperarAnimalito del Componente A (Servicio de animalitos), podría montar un repo de mentirijilla :

    public class RepositorioDeAnimalitosStub implements RepositorioDeAnimalitos {
        public Optional<Animalito> findById(Long id) {
            return Optional.of(Animalito.builder().nombre("Firulais").id(33).tipo("Perro").edad(2).build());
        }
    }
    ESTE STUB me sirve para la prueba: Recuperar animalito que existe

En ocasiones me interesa controlar la respuesta que devuelve el componente B, teniendo en cuenta los argumentos que recibe (1): FAKE

    public class RepositorioDeAnimalitosStub implements RepositorioDeAnimalitos {
        public Optional<Animalito> findById(Long id) {
            if(id<0) return Optional.empty()
            return Optional.of(Animalito.builder().nombre("Firulais").id(id).tipo("Perro").edad(2).build());
        }
    }

    Este test double me da más juego.... lo puedo usar en diferentes pruebas.
        ESTE test double me sirve para la prueba: Recuperar animalito que existe
        ESTE test double me sirve para la prueba: Recuperar animalito que no existe
    Llevado al extremo, un FAKE, si empiezo a añadirle más y más lógica se acaba convirtiendo en la implementación real

En ocasiones me interesa centrarme en los datos que se reciben (1). Por ejemplo, porque no vaya a hacer uso de la respuesta, o porque que ni siquiera haya respuesta. SPY

    @Getter
    public class EmailsServiceSpy implements EmailsService {
        private String destinatario;
        private String asunto;
        private String cuerpo;
        public void enviarEmail(String destinatario, String asunto, String cuerpo) {
            this.destinatario=destinatario;
            this.asunto=asunto;
            this.cuerpo=cuerpo;
        }
    }

    Para usar el Spy en la prueba;
    - Fuerzo a que se use el SPY en lugar del componente real
    - Llamo al altaAnimalito en el ServicioDeAnimalitos
    - Cuando acaba... y he revisado lo correspondiente al servicio de animalitos, reviso el SPY para ver si se ha llamado a la función enviarEmail con los parámetros que esperaba.
        Assertions.assertEquals(miEmailsServiceSpy.getDestinatario() ,"altas@animalitos.fermin.com");
        Assertions.assertEquals(miEmailsServiceSpy.getAsunto() ,"Alta de animalito");
        Assertions.assertEquals(miEmailsServiceSpy.getCuerpo() ,"Se ha dado de alta el animalito Firulais");

En ocasiones, en lugar de un SPY, monto un MOCK

    @Getter
    public class EmailsServiceMock implements EmailsService {
        private String destinatario;
        private String asunto;
        private String cuerpo;
        public void estosSonLosArgumentosConLosQueTeDeberíanLlamar(String destinatario, String asunto, String cuerpo) {
            this.destinatario=destinatario;
            this.asunto=asunto;
            this.cuerpo=cuerpo;
        }
        public void enviarEmail(String destinatario, String asunto, String cuerpo) {
            Assertions.assertEquals(getDestinatario() ,destinatario);
            Assertions.assertEquals(getAsunto(), asunto)
            Assertions.assertEquals(getCuerpo(), cuerpo)
        }
        public asegurameQueTeHanLlamado(){
            Assertions.assertTrue(getDestinatario()==null&&getAsunto()==null&&getCuerpo()==null);
        }
    }

    Para usar el Mock en la prueba;
    - Fuerzo a que se use el Mock en lugar del componente real
    - PreConfiguro el mock para que espere los datos que me interesan en esta prueba:
        miEmailsServiceMock.estosSonLosArgumentosConLosQueTeDeberíanLlamar("altas@animalitos.fermin.com", "Alta de animalito", "Se ha dado de alta el animalito Firulais");
    - Llamo al altaAnimalito en el ServicioDeAnimalitos
    - Cuando acaba... y he revisado lo correspondiente al servicio de animalitos, le pregunto al mock si se ha llamado a la función enviarEmail con los parámetros que esperaba.
        miEmailsServiceMock.asegurameQueTeHanLlamado();

    El Mock es más flexible.

En ocasiones no me interesa centrarme ni en los datos que se reciben (1) ni en lo que se devuelve (2): DUMMY

    public class EmailsServiceDummy implements EmailsService {
        public void enviarEmail(String destinatario, String asunto, String cuerpo) {
        }
    }

    Para qué sirve un dummy? 
    - Cuando necesito una implementación que no existe... pero que es necesaria para que el código se ejecute.
    - Muchas veces montamos dummies para hacer que las pruebas se ejecuten más rápido.

    Componente A ----(1)-----> ComponenteB
                 <---(2)------

                 Me centro en:
                    - (1) ->        Spy   >   Mock
                    - (2) ->        Stub  >   Fake (Tiene en cuente (1))
                    - ninguno:      Dummy

Estos objetos de prueba podemos crearlos nosotros (si son sencillitos), pero si son más complejos, me es muy tedioso.
Y en esos caso usamos librerías como MOCKITO !

A Mockito le paso una Interfaz... y se encarga de montar una clase que extienda esa interfaz con los retornos (return) más básicos que se puedan hacer:
- boolean: false
- int: 0
- Objeto: null

Y me permite a posteriori cambiar el comportamiento de esos retornos (de los que me interese, cuando me interese)

---

# Datos de usuarios

USUARIOS
ROLES
GRUPOS

# Datos de clientes

EMPRESAS
PERSONAS DE CONTACTO
DIRECCIONES

# CONTRATOS

Contratos