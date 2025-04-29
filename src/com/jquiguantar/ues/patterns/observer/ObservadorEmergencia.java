package com.jquiguantar.ues.patterns.observer;

import com.jquiguantar.ues.model.emergencies.Emergencia;

public interface ObservadorEmergencia {
    /**
     * Método que se llama cuando hay una actualización en la emergencia.
     * 
     * @param emergencia La emergencia que ha cambiado.
     */
    void actualizar(Emergencia emergencia);
}
