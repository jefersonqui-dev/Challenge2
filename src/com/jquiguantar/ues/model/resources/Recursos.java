package com.jquiguantar.ues.model.resources;
import com.jquiguantar.ues.utils.Ubicacion;
/************* ✨ Windsurf Command 🌟 *************/
/**
 * Representa un recurso para atender emergencias urbanas. Puede ser
 * un vehiculo de bomberos, una ambulancia, un policia, un bombero,
 * un paramedico o un policia. Cada recurso se identifica con un
 * id unico y tiene una ubicacion actual. Ademas, cada recurso
 * tiene un estado de disponibilidad que indica si esta disponible
 * para atender una emergencia.
 * 
 * Creamos la clase recursos definimos sus caracteristicas como atributos
 * private(para encapsular los datos),
 * creamos un constructor para inicializar esos atributos al crear objetos, y
 * creamos los getters y setters para permitir el acceso controlado
 * a esos datos desde fuera de la clase. esto establece la base para todos
 * los tipos especificos de recursos que crearemos despues usando HERENCIA
 */
public class Recursos {

    private String id; // cada recurso necesita ser identificado de forma unica
    private TipoRecurso tipo;
    //private String ubicacionActual;
    private boolean disponible;
    private Ubicacion ubicacionActual;

    public Recursos(String id, TipoRecurso tipo, Ubicacion ubicacionActual) {
        this.id = id;
        this.tipo = tipo;
        this.ubicacionActual = ubicacionActual;
        this.disponible = true;
    }

    // getters Permiten modifica el valor de un atrubuto
    public String getId() {
        return id;
    }

    public TipoRecurso getTipo() {
        return tipo;
    }

    public Ubicacion getUbicacionActual() {
        return ubicacionActual;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(TipoRecurso tipo) {
        this.tipo = tipo;
    }

    public void setUbicacionActual(Ubicacion ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

}
/******* d5f8b475-0c5f-483e-b286-8dc68ed7972f *******/
