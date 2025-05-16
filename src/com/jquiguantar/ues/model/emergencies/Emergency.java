package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.util.Ubicacion;

public class Emergency {

    private EmergencyType tipo;
    private SeverityLevel nivelGravedad;
    private Ubicacion ubicacion;
    private long tiempoEstimado;
    private boolean atendida;

    public Emergency(EmergencyType tipo, SeverityLevel nivelGravedad, Ubicacion ubicacion, long tiempoEstimado) {
        this.tipo = tipo;
        this.nivelGravedad = nivelGravedad;
        this.ubicacion = ubicacion;
        this.tiempoEstimado = tiempoEstimado;
        // this.atendida = atendida;

    }

    public EmergencyType getTipo() {
        return tipo;
    }

    public SeverityLevel getNivelGravedad() {
        return nivelGravedad;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public long getTiempoEstimado() {
        return tiempoEstimado;
    }

    public boolean isAtendida() {
        return atendida;
    }

    @Override
    public String toString() {
        return "Emergencia [tipo=" + tipo +
                ", nivelGravedad=" + nivelGravedad +
                ", ubicacion=" + ubicacion +
                ", tiempoEstimado=" + tiempoEstimado +
                ", atendida=" + atendida + "]";
    }

}
