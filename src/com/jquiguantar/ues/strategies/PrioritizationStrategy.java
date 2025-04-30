package com.jquiguantar.ues.strategies;

import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.resources.Recursos;

import java.util.List;

/**
 * Interfaz para definir diferentes estrategias de priorización de emergencias.
 */
public interface PrioritizationStrategy {

    /**
     * Selecciona la siguiente emergencia a atender basada en la estrategia implementada.
     *
     * @param emergenciasPendientes Lista de emergencias actualmente en estado PENDIENTE.
     * @param recursosDisponibles Lista de recursos actualmente disponibles.
     * @return La emergencia seleccionada como prioritaria, o null si no se puede seleccionar ninguna
     *         según esta estrategia o no hay emergencias/recursos adecuados.
     */
    Emergencia seleccionarSiguiente(List<Emergencia> emergenciasPendientes, List<Recursos> recursosDisponibles);

    /**
     * Devuelve un nombre descriptivo para la estrategia (útil para la UI).
     * @return El nombre de la estrategia.
     */
    String getNombreEstrategia();
} 