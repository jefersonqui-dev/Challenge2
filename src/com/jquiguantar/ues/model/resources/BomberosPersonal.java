package com.jquiguantar.ues.model.resources;

import com.jquiguantar.ues.utils.Ubicacion;

public class BomberosPersonal extends Recursos {
    // constructor
    public BomberosPersonal(String id, Ubicacion ubicacionActual) {
        // llamamos al constructor de la clase padre
        // le pasamos el id, el tipo correcto para bomberos, y la ubicacion
        // inicial
        super(id, TipoRecurso.BOMBEROS_PERSONAL, ubicacionActual);
    }
}
