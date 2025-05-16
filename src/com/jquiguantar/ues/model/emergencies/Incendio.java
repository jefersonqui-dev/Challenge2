package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.util.Ubicacion;

public class Incendio extends Emergencia {
    public Incendio(Ubicacion ubicacion, NivelGravedad nivelGravedad, long tiempoEstimado) {
        super(TipoEmergencia.INCENDIO, nivelGravedad, ubicacion, tiempoEstimado);
    }
}
