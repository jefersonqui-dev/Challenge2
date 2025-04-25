package com.jquiguantar.ues.services;

import com.jquiguantar.ues.model.emergencies.Emergencia;

//Esta Interfaz define el "Contrato": Especifica que métodos deben tener las clsaes 
//que quieran ser consideradas capaces de 
//gestionar emergencias
public interface GestionEmergencia {

    /**
     * asignar recursos de este servicio a la emergencia dada.
     * 
     * @param emergencia La emergencia a registrar
     * @return true si el registro fue exitoso, false en caso contrario
     */
    boolean registrarEmergencia(Emergencia emergencia);

    /**
     * Simula el progreso o la resolucion de la emergencia por parte de este
     * servicio.
     * 
     * @param emergencia La emergencia a resolver
     * @return true si la emergencia se considera resuelta, false en caso contrario
     */
    boolean resolverEmergencia(Emergencia emergencia);


}
