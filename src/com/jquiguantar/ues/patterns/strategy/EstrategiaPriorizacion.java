package com.jquiguantar.ues.patterns.strategy;

import com.jquiguantar.ues.model.emergencies.Emergencia;
import java.util.List;

/**
 * Interfaz para definir la estrategia de priorización de emergencias.
 * Permite implementar diferentes algoritmos de priorización.
 */
public interface EstrategiaPriorizacion {
    List<Emergencia> priorizar(List<Emergencia> emergencias);
}
