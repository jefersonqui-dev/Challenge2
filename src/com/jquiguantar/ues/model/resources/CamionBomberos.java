package com.jquiguantar.ues.model.resources;

public class CamionBomberos extends Recursos {

    // constructor
    public CamionBomberos(String id, String ubicacionActual) {
        // llamamos al constructor de la clase padre
        // le pasamos el id, el tipo correcto para camionBomberos, y la ubicacion
        // inicial
        super(id, TipoRecurso.BOMBEROS_UNIT, ubicacionActual);
    }
}
