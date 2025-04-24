package com.jquiguantar.ues.model.resources;

public class ParamedicosPersonal extends Recursos {
    // constructor
    public ParamedicosPersonal(String id, String ubicacionActual) {
        // llamamos al constructor de la clase padre
        // le pasamos el id, el tipo correcto para bomberos, y la ubicacion
        // inicial
        super(id, TipoRecurso.PARAMEDICO_PERSONAL, ubicacionActual);
    }
}
