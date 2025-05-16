package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.util.Ubicacion;

public class AccidenteVehicular extends Emergencia {
    public AccidenteVehicular(Ubicacion ubicacion, NivelGravedad gravedad, long tiempoEstimado) {
        super(TipoEmergencia.ACCIDENTE_VEHICULAR, gravedad, ubicacion, tiempoEstimado);
    }
}
