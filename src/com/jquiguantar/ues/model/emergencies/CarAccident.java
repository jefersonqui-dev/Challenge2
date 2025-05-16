package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.util.Ubicacion;

public class CarAccident extends Emergency {
    public CarAccident(Ubicacion ubicacion, SeverityLevel gravedad, long tiempoEstimado) {
        super(EmergencyType.ACCIDENTE_VEHICULAR, gravedad, ubicacion, tiempoEstimado);
    }
}
