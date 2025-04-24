package com.jquiguantar.ues.services;

import com.jquiguantar.ues.model.emergencies.Emergencia;

public interface GestionEmergencia {
    // Método para asignar recursos a una emergencia específica
    // Recibe la emergencia a la que se asignarán los recursos
    // Podría devolver true/false indicando si fue posible asignar
    boolean registrarEmergencia(Emergencia emergencia);

    // Método para simular la resolución o progreso de una emergencia
    // Podría recibir la emergencia y quizás un indicador de progreso o estado
    // Podría devolver true/false si la emergencia se considera resuelta
    boolean resolverEmergencia(Emergencia emergencia);

    // String getNombreServicio();
}
