package com.jquiguantar.ues.model.resources;

import com.jquiguantar.ues.utils.Ubicacion;

public class ParamedicosPersonal extends Recursos {
    // constructor
    public ParamedicosPersonal(String id, Ubicacion ubicacionActual) {
        // llamamos al constructor de la clase padre
        // le pasamos el id, el tipo correcto para bomberos, y la ubicacion
        // inicial
        super(id, TipoRecurso.PARAMEDICO_PERSONAL, ubicacionActual);
    }
}
