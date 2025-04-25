package com.jquiguantar.ues.services;

import com.jquiguantar.ues.model.emergencies.Emergencia;

public class PoliciaService implements GestionEmergencia {
    private String nombreServicio = "Policia";

    // Constructor
    public PoliciaService() {
    }

    /**
     * Implementación del método para registrar una emergencia específica de
     * policia.
     * 
     * @param emergencia La emergencia a registrar.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
    @Override
    public boolean registrarEmergencia(Emergencia emergencia) {
        return false;
    }

    /**
     * Implementación del método para simular la resolución de una emergencia
     * por parte del servicio de policia.
     * 
     * @param emergencia La emergencia a resolver.
     * @return true si la emergencia se considera resuelta, false en caso contrario.
     */
    @Override
    public boolean resolverEmergencia(Emergencia emergencia) {
        return false;
    }

    public String getNombreServicio() {
        return this.nombreServicio;
    }
}
