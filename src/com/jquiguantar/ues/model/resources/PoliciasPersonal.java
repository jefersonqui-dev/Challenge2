package com.jquiguantar.ues.model.resources;

import com.jquiguantar.ues.utils.Ubicacion;

public class PoliciasPersonal extends Recursos {
    // constructor
    public PoliciasPersonal(String id, Ubicacion ubicacionActual) {
        // llamamos al constructor de la clase padre
        // le pasamos el id, el tipo correcto para bomberos, y la ubicacion
        // inicial
        super(id, TipoRecurso.POLICIA_PERSONAL, ubicacionActual);
    }
}
