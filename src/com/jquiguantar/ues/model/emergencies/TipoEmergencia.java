package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.resources.TipoRecurso;
import java.util.Map;

public interface TipoEmergencia {
    // Devuelve el nombre descriptivo del tipo de emergencia
    String getNombre();

    // Devuelve el contador de emergencias de este tipo
    int getContador();

    Map<TipoRecurso, Integer> calcularRecursosInicialesNecesarios(NivelGravedad gravedad);
}
