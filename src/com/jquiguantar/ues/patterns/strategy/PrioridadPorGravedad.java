package com.jquiguantar.ues.patterns.strategy;

import com.jquiguantar.ues.model.emergencies.Emergencia;
import java.util.List;
import com.jquiguantar.ues.model.emergencies.NivelGravedad;
import java.util.Comparator;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class PrioridadPorGravedad implements EstrategiaPriorizacion {

    @Override
    public List<Emergencia> priorizar(List<Emergencia> emergencias) {
        List<Emergencia> listaPriorizada = new ArrayList<>(emergencias);

        // Ordena la lista usando el comparador
        Collections.sort(listaPriorizada, Comparator.comparing(Emergencia::getNivelGravedad).reversed());

        // si usando Streams quedaria asi:
        /*
         * List<Emergencia> listaPriorizada = emergencias.stream()
         * .sorted(Comparator.comparing(Emergencia::getNivelGravedad).reversed())
         * .collect(Collectors.toList());
         */
        return listaPriorizada;
    }

}
