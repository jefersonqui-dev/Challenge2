package com.jquiguantar.ues.patterns.strategy;

import com.jquiguantar.ues.model.emergencies.Emergencia;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.ArrayList;

//Esta estrategia ordenara las emergencias por su tiempo de inicio, las mas antiguas primero
public class PrioridadPorAntiguedad implements EstrategiaPriorizacion {

    @Override
    public List<Emergencia> priorizar(List<Emergencia> emergencias) {
        List<Emergencia> listaPriorizada = new ArrayList<>(emergencias);

        // Ordena la lista usando el comparador
        Collections.sort(listaPriorizada, Comparator.comparing(Emergencia::getTiempoInicio));

        // si usando Streams quedaria asi:
        /*
         * List<Emergencia> listaPriorizada = emergencias.stream()
         * .sorted(Comparator.comparing(Emergencia::getTiempoInicio))
         * .collect(Collectors.toList());
         */
        return listaPriorizada;
}
