package com.jquiguantar.ues.services;

import com.jquiguantar.ues.model.emergencies.Emergencia;

public class AmbulanciaService implements GestionEmergencia {
    private String nombreServicio = "Ambulancias";

    // Constructor
    public AmbulanciaService() {
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
        System.out.println("AmbulanciaService registrando emergencia..."); // Simulación de registro
        return true; // O false dependiendo del resultado de tu lógica
    }

    /**
     * Implementación del método para simular la resolución de una emergencia
     * por parte del servicio de ambulancias.
     * 
     * @param emergencia La emergencia a resolver.
     * @return true si la emergencia se considera resuelta, false en caso contrario.
     */
    @Override
    public boolean resolverEmergencia(Emergencia emergencia) {
        System.out.println("AmbulanciaService resolviendo emergencia..."); // Simulación de resolución
        return true; // O false dependiendo del resultado de tu lógica
    }
}
