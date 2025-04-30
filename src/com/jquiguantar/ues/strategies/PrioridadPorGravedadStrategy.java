package com.jquiguantar.ues.strategies;

import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.emergencies.EstadoEmergencia;
import com.jquiguantar.ues.model.emergencies.NivelGravedad;
import com.jquiguantar.ues.model.resources.Recursos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Estrategia que prioriza la emergencia PENDIENTE con el mayor nivel de gravedad.
 */
public class PrioridadPorGravedadStrategy implements PrioritizationStrategy {

    @Override
    public Emergencia seleccionarSiguiente(List<Emergencia> emergenciasPendientes, List<Recursos> recursosDisponibles) {
        if (emergenciasPendientes == null || emergenciasPendientes.isEmpty() || recursosDisponibles == null || recursosDisponibles.isEmpty()) {
            return null; // No hay emergencias pendientes o recursos disponibles
        }

        // Filtra solo las pendientes (aunque la lista de entrada ya debería estar filtrada)
        // y encuentra la de máxima gravedad.
        // NivelGravedad.ordinal() devuelve un int (MENOR=0, MODERADO=1, ALTO=2, CRITICO=3)
        // por lo que queremos el máximo ordinal.
        Optional<Emergencia> emergenciaPrioritaria = emergenciasPendientes.stream()
                .filter(e -> e.getEstado() == EstadoEmergencia.PENDIENTE)
                .max(Comparator.comparingInt(e -> e.getNivelGravedad().ordinal()));

        return emergenciaPrioritaria.orElse(null);
    }

    @Override
    public String getNombreEstrategia() {
        return "Prioridad por Gravedad";
    }
} 