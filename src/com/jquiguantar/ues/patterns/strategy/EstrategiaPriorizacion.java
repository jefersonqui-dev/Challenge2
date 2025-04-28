package com.jquiguantar.ues.patterns.strategy;

import com.jquiguantar.ues.model.emergencies.Emergencia;
import java.util.List;

/**
 * Interfaz para definir la estrategia de priorización de emergencias.
 * Permite implementar diferentes algoritmos de priorización.
 */
public interface EstrategiaPriorizacion {

    /**
     * priorizar una lista de emergencias.
     * 
     * @param emergencias Lista de emergencias a priorizar.
     * @return Lista de emergencias priorizadas
     */
    List<Emergencia> priorizar(List<Emergencia> emergencias);

}
