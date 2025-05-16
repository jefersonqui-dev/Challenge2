package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.util.Ubicacion;

public class Heist extends Emergency {
    public Heist(Ubicacion ubicacion, SeverityLevel nivelGravedad, long tiempoEstimado) {
        super(EmergencyType.ROBO, nivelGravedad, ubicacion, tiempoEstimado);
    }
}