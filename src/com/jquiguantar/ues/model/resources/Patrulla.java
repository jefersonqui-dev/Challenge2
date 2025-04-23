package com.jquiguantar.ues.model.resources;

public class Patrulla extends Recursos {
    // constructor
    public Patrulla(String id, String ubicacionActual) {
        // llamamos al constructor de la clase padre
        // le pasamos el id, el tipo correcto para camionBomberos, y la ubicacion
        // inicial
        super(id, TipoRecurso.POLICIA_UNIT, ubicacionActual);
    }
}
