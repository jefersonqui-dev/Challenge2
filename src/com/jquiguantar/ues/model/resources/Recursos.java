package com.jquiguantar.ues.model.resources;

// para que los tipos de recursos esten predefinidos y evitar errores tipograficos como bombero vs Bombero
enum TipoRecurso {
    BOMBEROS_UNIT,
    AMBULANCIAS_VEHICULO,
    POLICIA_UNIT,
    BOMBEROS_PERSONAL,
    PARAMEDICO_PERSONAL,
    POLICIA_PERSONAL
}

/************* ✨ Windsurf Command 🌟 *************/
/**
 * Representa un recurso para atender emergencias urbanas. Puede ser
 * un vehiculo de bomberos, una ambulancia, un policia, un bombero,
 * un paramedico o un policia. Cada recurso se identifica con un
 * id unico y tiene una ubicacion actual. Ademas, cada recurso
 * tiene un estado de disponibilidad que indica si esta disponible
 * para atender una emergencia.
 * 
 * 
 */
public class Recursos {

    private String id; // cada recurso necesita ser identificado de forma unica
    private TipoRecurso tipo;
    private String ubicacionActual;
    private boolean disponible;

    public Recursos(String id, TipoRecurso tipo, String ubicacionActual) {
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

    public String getUbicacionActual() {
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

    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

}
/******* d5f8b475-0c5f-483e-b286-8dc68ed7972f *******/
