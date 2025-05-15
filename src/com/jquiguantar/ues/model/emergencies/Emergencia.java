package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.util.Ubicacion;

public class Emergencia {

    private TipoEmergencia tipo;
    private NivelGravedad nivelGravedad;
    private Ubicacion ubicacion;
    private long tiempoEstimado;
    private boolean atendida;

    public Emergencia(TipoEmergencia tipo, NivelGravedad nivelGravedad, Ubicacion ubicacion, long tiempoEstimado) {
        this.tipo = tipo;
        this.nivelGravedad = nivelGravedad;
        this.ubicacion = ubicacion;
        this.tiempoEstimado = tiempoEstimado;
        this.atendida = atendida;

    }

    public TipoEmergencia getTipo() {
        return tipo;
    }

    public NivelGravedad getNivelGravedad() {
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

}
