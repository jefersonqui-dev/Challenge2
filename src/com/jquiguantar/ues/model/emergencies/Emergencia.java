package com.jquiguantar.ues.model.emergencies;

import com.jquiguantar.ues.model.resources.Recursos;
import java.time.LocalDateTime; //Para la fecha y hora
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.jquiguantar.ues.utils.Ubicacion;

public class Emergencia {
    // Contador estático para generar IDs secuenciales
    private static long contadorId = 0;

    private String id;
    private TipoEmergencia tipo;
    //private String ubicacion;
    private NivelGravedad nivelGravedad;
    private EstadoEmergencia estado;
    private LocalDateTime tiempoInicio;
    private LocalDateTime tiempoFin;
    private List<Recursos> recursosAsignados;
    private Ubicacion ubicacion;

    // Constructor
    public Emergencia(TipoEmergencia tipo, Ubicacion ubicacion, NivelGravedad nivelGravedad) {
        // Generar ID secuencial con prefijo
        contadorId++;
        this.id = "EMG-" + contadorId;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.nivelGravedad = nivelGravedad;
        this.estado = EstadoEmergencia.PENDIENTE; // una nueva emergencia siempre empieza pendiente
        this.tiempoInicio = LocalDateTime.now(); // Registra el momento Actual
        this.tiempoFin = null; // Inicialmente no resuelta
        this.recursosAsignados = new ArrayList<>(); // Empieza con una lista vacia de recursos
    }

    // Getters
    public String getId() {
        return id;
    }

    public TipoEmergencia getTipo() {
        return tipo;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public NivelGravedad getNivelGravedad() {
        return nivelGravedad;
    }

    public EstadoEmergencia getEstado() {
        return estado;
    }

    public LocalDateTime getTiempoInicio() {
        return tiempoInicio;
    }

    public LocalDateTime getTiempoFin() {
        return tiempoFin;
    }

    public List<Recursos> getRecursosAsignados() {
        return recursosAsignados;
    }

    // Setters
    public void setEstado(EstadoEmergencia estado) {
        this.estado = estado;
        // Si la emergencia se resuelve, se registra el momento actual
        if (estado == EstadoEmergencia.RESUELTA) {
            this.tiempoFin = LocalDateTime.now();
        }
    }

    public void setRecursosAsignados(List<Recursos> recursosAsignados) {
        this.recursosAsignados = recursosAsignados;
    }

    // Metodo para añadir un recurso asignado a la lista
    public void agregarRecursoAsignado(Recursos recurso) {
        if (recurso != null) {
            this.recursosAsignados.add(recurso);
        }
    }
    // No necesitamos setters para id, tipo, ubicacion
    // nivel gravedad, tiempoInicio
    // una vez la emergencia ha sido creada
}
