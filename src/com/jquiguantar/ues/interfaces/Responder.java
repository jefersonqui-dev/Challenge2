package com.jquiguantar.ues.interfaces;

import com.jquiguantar.ues.model.emergencies.Emergencia;

/**
 * Define las acciones que puede realizar una entidad capaz de responder a emergencias.
 * Las clases que implementen esta interfaz (como las de Recursos) podrán
 * ser asignadas para atender emergencias y reportar su estado de disponibilidad.
 */
public interface Responder {

    /**
     * Indica a la entidad que comience a atender la emergencia especificada.
     * La implementación de este método debería actualizar el estado interno
     * de la entidad (por ejemplo, marcarla como no disponible).
     *
     * @param emergencia La emergencia que debe ser atendida.
     */
    void atenderEmergencia(Emergencia emergencia);

    /**
     * Devuelve el estado actual de disponibilidad de la entidad respondedora.
     *
     * @return true si la entidad está disponible para una nueva tarea, false en caso contrario.
     */
    boolean isDisponible();

} 