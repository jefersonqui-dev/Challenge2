package com.jquiguantar.ues.model.resources;

public class AmbulanciaVehiculo extends Recursos {

    // Constructor
    public AmbulanciaVehiculo(String id, String ubicacionActual) {
        // llamamos al constructor de la clase padre
        // le pasamos el id, el tipo correcto para camionBomberos, y la ubicacion
        // inicial
        super(id, TipoRecurso.AMBULANCIAS_VEHICULO, ubicacionActual);
    }

}
