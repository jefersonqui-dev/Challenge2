package com.jquiguantar.ues.strategies;

import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.emergencies.EstadoEmergencia;
import com.jquiguantar.ues.model.resources.Recursos;
import com.jquiguantar.ues.utils.Ubicacion;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Estrategia que prioriza la emergencia PENDIENTE más cercana a cualquier recurso disponible.
 */
public class PrioridadPorProximidadStrategy implements PrioritizationStrategy {

    @Override
    public Emergencia seleccionarSiguiente(List<Emergencia> emergenciasPendientes, List<Recursos> recursosDisponibles) {
        if (emergenciasPendientes == null || emergenciasPendientes.isEmpty() || recursosDisponibles == null || recursosDisponibles.isEmpty()) {
            return null; // No hay emergencias pendientes o recursos disponibles
        }

        Emergencia emergenciaMasCercana = null;
        double distanciaMinimaGlobal = Double.MAX_VALUE;

        for (Emergencia emergencia : emergenciasPendientes) {
            if (emergencia.getEstado() != EstadoEmergencia.PENDIENTE) {
                continue; // Solo consideramos pendientes
            }

            // Encontrar la distancia mínima desde esta emergencia a CUALQUIER recurso disponible
            Optional<Double> distanciaMinimaAEsteRecurso = recursosDisponibles.stream()
                    .filter(Recursos::isDisponible) // Doble chequeo por si acaso
                    .map(recurso -> emergencia.getUbicacion().distanciaHasta(recurso.getUbicacionActual()))
                    .min(Double::compareTo);

            // Si encontramos una distancia y es menor que la mínima global actual,
            // actualizamos la emergencia más cercana.
            if (distanciaMinimaAEsteRecurso.isPresent()) {
                double distancia = distanciaMinimaAEsteRecurso.get();
                if (distancia < distanciaMinimaGlobal) {
                    distanciaMinimaGlobal = distancia;
                    emergenciaMasCercana = emergencia;
                }
            }
        }

        return emergenciaMasCercana;
    }

    @Override
    public String getNombreEstrategia() {
        return "Prioridad por Proximidad";
    }
} 