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
}
