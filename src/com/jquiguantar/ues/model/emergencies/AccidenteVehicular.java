package com.jquiguantar.ues.model.emergencies;

import java.util.Map;
import com.jquiguantar.ues.model.resources.TipoRecurso;
import java.util.HashMap;

public class AccidenteVehicular implements TipoEmergencia {

    @Override
    public String getNombre() {
        return "Accidente Vehicular";
    }

    @Override
    public Map<TipoRecurso, Integer> calcularRecursosInicialesNecesarios(NivelGravedad gravedad) {
        Map<TipoRecurso, Integer> recursos = new HashMap<>();

        switch (gravedad) {
            case BAJO:
                recursos.put(TipoRecurso.POLICIA_UNIT, 1);
                recursos.put(TipoRecurso.POLICIA_PERSONAL, 2);
                break;
            case MEDIO:
                recursos.put(TipoRecurso.POLICIA_UNIT, 2);
                recursos.put(TipoRecurso.POLICIA_PERSONAL, 4);
                recursos.put(TipoRecurso.AMBULANCIAS_VEHICULO, 1);
                recursos.put(TipoRecurso.PARAMEDICO_PERSONAL, 2);
                break;
            case ALTO:
                recursos.put(TipoRecurso.POLICIA_UNIT, 3);
                recursos.put(TipoRecurso.POLICIA_PERSONAL, 6);
                recursos.put(TipoRecurso.AMBULANCIAS_VEHICULO, 2);
                recursos.put(TipoRecurso.PARAMEDICO_PERSONAL, 4);
                recursos.put(TipoRecurso.BOMBEROS_UNIT, 1);
                recursos.put(TipoRecurso.BOMBEROS_PERSONAL, 2);
                break;
            default:
                break;
        }
        return recursos;
    }
}
