package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.util.Ubicacion;

public class Fire extends Emergency {
    public Fire(Ubicacion ubicacion, SeverityLevel nivelGravedad, long tiempoEstimado) {
        super(EmergencyType.INCENDIO, nivelGravedad, ubicacion, tiempoEstimado);
    }
}
