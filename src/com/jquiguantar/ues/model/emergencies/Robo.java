package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.util.Ubicacion;

public class Robo extends Emergencia {
    public Robo(Ubicacion ubicacion, NivelGravedad nivelGravedad, long tiempoEstimado) {
        super(TipoEmergencia.ROBO, nivelGravedad, ubicacion, tiempoEstimado);
    }
}