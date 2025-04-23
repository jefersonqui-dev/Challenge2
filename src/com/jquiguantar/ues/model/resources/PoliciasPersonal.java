package com.jquiguantar.ues.model.resources;

public class PoliciasPersonal extends Recursos {
    // constructor
    public PoliciasPersonal(String id, String ubicacionActual) {
        // llamamos al constructor de la clase padre
        // le pasamos el id, el tipo correcto para bomberos, y la ubicacion
        // inicial
        super(id, TipoRecurso.POLICIA_PERSONAL, ubicacionActual);
    }
}
