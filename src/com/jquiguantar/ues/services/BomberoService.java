package com.jquiguantar.ues.services;

import com.jquiguantar.ues.model.emergencies.Emergencia;

public class BomberoService implements GestionEmergencia {

    private String nombreServicio = "Bomberos";

    // Constructor
    public BomberoService() {
    }

    /**
     * Implementación del método para registrar una emergencia específica de
     * ambulancia.
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
     * por parte del servicio de ambulancias.
     * 
     * @param emergencia La emergencia a resolver.
     * @return true si la emergencia se considera resuelta, false en caso contrario.
     */
    @Override // Buena práctica
    public boolean resolverEmergencia(Emergencia emergencia) {
        System.out.println("AmbulanciaService resolviendo emergencia...");

        return true; // O false dependiendo del resultado de tu lógica
    }

}
