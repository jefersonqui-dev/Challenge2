package com.jquiguantar.ues.services;

import com.jquiguantar.ues.model.emergencies.Emergencia;

public class BomberoService implements GestionEmergencia {
    private String nombreServicio = "Bomberos";

    // Constructor
    public BomberoService() {

    }

    @Override
    public boolean asignarRecursos(Emergencia emergencia) {
        System.out.println(nombreServicio + "Intentando asignar Recursos a emergencia " + emergencia.getId() + " ("
                + emergencia.getTipo().getNombreServicio);
        return false;
    }
}
