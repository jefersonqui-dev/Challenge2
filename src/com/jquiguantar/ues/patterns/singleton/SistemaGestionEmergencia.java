package com.jquiguantar.ues.patterns.singleton;

//al Implementar el patrón Singleton en esta clase 
//sabemos que esto garantizara que solo se pueda crear 
//una única instancia de ella durante toda la ejecución del programa.
import com.jquiguantar.ues.model.emergencies.Emergencia;

import com.jquiguantar.ues.model.emergencies.EstadoEmergencia;
import com.jquiguantar.ues.model.resources.TipoRecurso;
import com.jquiguantar.ues.services.GestionEmergencia;

import com.jquiguantar.ues.model.resources.Recursos;
import com.jquiguantar.ues.services.AmbulanciaService;
import com.jquiguantar.ues.services.BomberoService;
import com.jquiguantar.ues.services.PoliciaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;//Necesario para usar lambdas con streams

public class SistemaGestionEmergencia {
    // Definimos los atributos de esta clase central que necesita para mantener el
    // estado del sistema
    // (lista de emrgencias y recursos disponibles)

    // atributo estatico privado que contendra la unica instancia de la clase
    private static SistemaGestionEmergencia instance;

    // atributos para mantener el estado global del sistema(listas)

    private List<Emergencia> emergenciasActivas; // EMERGENCIA PENDIENTE O EN PROCESO
    private List<Emergencia> emergenciasResueltas; // EMERGENCIA RESUELTA
    private List<Recursos> recursosDisponibles; // RECURSO DISPONIBLE
    private List<Recursos> recursosOcupados; // RECURSO OCUPADO
    private List<GestionEmergencia> serviciosOperacionales; // SERVICIO DE GESTION DE EMERGENCIA
    private List<Emergencia> colaEmergencias; // Nueva lista para emergencias en espera

    // Límites de recursos por tipo
    private static final Map<TipoRecurso, Integer> LIMITES_RECURSOS = Map.of(
        TipoRecurso.BOMBEROS_UNIT, 5,
        TipoRecurso.BOMBEROS_PERSONAL, 20,
        TipoRecurso.AMBULANCIAS_VEHICULO, 10,
        TipoRecurso.PARAMEDICO_PERSONAL, 30,
        TipoRecurso.POLICIA_UNIT, 8,
        TipoRecurso.POLICIA_PERSONAL, 24
    );

    // constructor privado para garantizar la unica instancia
    // Nadie fuera de esta clase puede usar 'New SistemaGestionEmergencia()'
    private SistemaGestionEmergencia() {
        // Inicializa las listas al crear la instancia
        this.emergenciasActivas = new ArrayList<>();
        this.emergenciasResueltas = new ArrayList<>();
        this.recursosDisponibles = new ArrayList<>();
        this.recursosOcupados = new ArrayList<>();
        // Inicializa los servicios
        this.serviciosOperacionales = new ArrayList<>();
        this.colaEmergencias = new ArrayList<>();
        this.serviciosOperacionales.add(new AmbulanciaService());
        this.serviciosOperacionales.add(new BomberoService());
        this.serviciosOperacionales.add(new PoliciaService());

        // LLama a un metodo de inicializacion de recursos
        inicializarRecursos();
        System.out.println("Sistema de Emergencias Urbanas Inicializado...");

    }

    /**
     * Metodo para obtener la instancia unica de esta clase.
     * 
     * @return La unica instancia de la clase SistemaGestionEmergencia.
     */
    public static SistemaGestionEmergencia getInstance() {
        // Si la instancia no ha sido creada, la creamos
        if (instance == null) {
            instance = new SistemaGestionEmergencia();
        }
        // Devuelve la instancia creada
        return instance;
    }

    // --- METODOS PARA GESTIONAR EL ESTADO DEL SISTEMA ---

    // Metodo para inicializar los recursos del sitema (crear instancias de recurso)
    private void inicializarRecursos() {
        // Inicializar recursos según los límites establecidos
        for (Map.Entry<TipoRecurso, Integer> entry : LIMITES_RECURSOS.entrySet()) {
            TipoRecurso tipo = entry.getKey();
            int cantidad = entry.getValue();
            
            for (int i = 1; i <= cantidad; i++) {
                String id = generarIdRecurso(tipo, i);
                String ubicacion = "Estacion Central";
                
                switch (tipo) {
                    case BOMBEROS_UNIT:
                        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.CamionBomberos(id, ubicacion));
                        break;
                    case BOMBEROS_PERSONAL:
                        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.BomberosPersonal(id, ubicacion));
                        break;
                    case AMBULANCIAS_VEHICULO:
                        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.AmbulanciaVehiculo(id, ubicacion));
                        break;
                    case PARAMEDICO_PERSONAL:
                        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.ParamedicoPersonal(id, ubicacion));
                        break;
                    case POLICIA_UNIT:
                        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.PoliciaUnit(id, ubicacion));
                        break;
                    case POLICIA_PERSONAL:
                        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.PoliciaPersonal(id, ubicacion));
                        break;
                }
            }
        }
    }

    private String generarIdRecurso(TipoRecurso tipo, int numero) {
        String prefijo = "";
        switch (tipo) {
            case BOMBEROS_UNIT: prefijo = "CB"; break;
            case BOMBEROS_PERSONAL: prefijo = "BP"; break;
            case AMBULANCIAS_VEHICULO: prefijo = "AV"; break;
            case PARAMEDICO_PERSONAL: prefijo = "PP"; break;
            case POLICIA_UNIT: prefijo = "PU"; break;
            case POLICIA_PERSONAL: prefijo = "PP"; break;
        }
        return prefijo + String.format("%03d", numero);
    }

    // METODO PARA REGISTRAR UNA NUEVA EMERGENCIA
    public void registrarEmergencia(Emergencia emergencia) {
        if (emergencia != null) {
            // Intentar asignar recursos inmediatamente
            if (asignarRecursosAEmergencia(emergencia.getId())) {
                this.emergenciasActivas.add(emergencia);
                System.out.println("Emergencia registrada y recursos asignados exitosamente.");
            } else {
                // Si no hay recursos disponibles, agregar a la cola
                this.colaEmergencias.add(emergencia);
                System.out.println("Emergencia registrada pero en espera de recursos. Posición en cola: " + 
                                 this.colaEmergencias.size());
            }
        }
    }
    // Metodos para obtener las listas

    public List<Emergencia> getEmergenciasActivas() {
        return emergenciasActivas;
    }

    public List<Recursos> getRecursosDisponibles() {
        return recursosDisponibles;
    }

    public List<Recursos> getRecursosOcupados() {
        return recursosOcupados;
    }

    public List<GestionEmergencia> getServiciosOperacionales() {
        return serviciosOperacionales;
    }

    public List<Emergencia> getColaEmergencias() {
        return colaEmergencias;
    }

    // METODO PARA ASIGNAR RECURSOS
    public boolean asignarRecursosAEmergencia(String idEmergencia) {
        System.out.println("Asignando recursos a la emergencia con ID: " + idEmergencia);
        // Busca la emergencia por ID
        Optional<Emergencia> emergenciaOpt = emergenciasActivas.stream()
                .filter(e -> e.getId().equals(idEmergencia))
                .findFirst();

        // verifica si selecciono una emergencia y si esta en estado pendiente
        if (!emergenciaOpt.isPresent()) {
            System.out.println("Error: Emergencia con ID: " + idEmergencia + " no encontrada o ya atendida.");
            return false;
        }
        Emergencia emergencia = emergenciaOpt.get();
        if (emergencia.getEstado() != EstadoEmergencia.PENDIENTE) {
            System.out.println("Error: Emergencia con ID: " + idEmergencia + " no esta PENDIENTE. Estado actual: "
                    + emergencia.getEstado());
            return false;
        }

        System.out.println("Procesando asignacion para emergencia: " + emergencia.getTipo().getNombre() + " ("
                + emergencia.getNivelGravedad() + ")");

        // 2. Determinar los recursos necesarios usando el Polimorfismo
        Map<TipoRecurso, Integer> recursosNecesarios = emergencia.getTipo()
                .calcularRecursosInicialesNecesarios(emergencia.getNivelGravedad());
        boolean recursosAsignadosExitosamente = true;

        // Verificar si hay suficientes recursos disponibles
        for (Map.Entry<TipoRecurso, Integer> entry : recursosNecesarios.entrySet()) {
            TipoRecurso tipo = entry.getKey();
            int cantidadNecesaria = entry.getValue();
            int cantidadDisponible = (int) recursosDisponibles.stream()
                    .filter(r -> r.getTipo() == tipo)
                    .count();

            if (cantidadDisponible < cantidadNecesaria) {
                System.out.println("No hay suficientes recursos de tipo " + tipo + 
                                 " (Necesarios: " + cantidadNecesaria + 
                                 ", Disponibles: " + cantidadDisponible + ")");
                recursosAsignadosExitosamente = false;
                break;
            }
        }

        if (recursosAsignadosExitosamente) {
            // Asignar los recursos
            for (Map.Entry<TipoRecurso, Integer> entry : recursosNecesarios.entrySet()) {
                TipoRecurso tipo = entry.getKey();
                int cantidadNecesaria = entry.getValue();
                int cantidadAsignada = 0;

                List<Recursos> recursosDelTipo = recursosDisponibles.stream()
                        .filter(r -> r.getTipo() == tipo)
                        .collect(Collectors.toList());

                for (Recursos recurso : recursosDelTipo) {
                    if (cantidadAsignada < cantidadNecesaria) {
                        recursosDisponibles.remove(recurso);
                        recursosOcupados.add(recurso);
                        recurso.setDisponible(false);
                        emergencia.agregarRecursoAsignado(recurso);
                        cantidadAsignada++;
                    }
                }
            }

            emergencia.setEstado(EstadoEmergencia.EN_PROGRESO);
            System.out.println("Recursos asignados exitosamente a la emergencia " + emergencia.getId());
            return true;
        } else {
            System.out.println("No se pudieron asignar recursos a la emergencia " + emergencia.getId());
            return false;
        }
    }

    // Método para liberar recursos cuando una emergencia se resuelve
    public void liberarRecursosEmergencia(String idEmergencia) {
        Optional<Emergencia> emergenciaOpt = emergenciasActivas.stream()
                .filter(e -> e.getId().equals(idEmergencia))
                .findFirst();

        if (emergenciaOpt.isPresent()) {
            Emergencia emergencia = emergenciaOpt.get();
            List<Recursos> recursosALiberar = new ArrayList<>(emergencia.getRecursosAsignados());
            
            for (Recursos recurso : recursosALiberar) {
                recursosOcupados.remove(recurso);
                recursosDisponibles.add(recurso);
                recurso.setDisponible(true);
                emergencia.getRecursosAsignados().remove(recurso);
            }

            emergenciasActivas.remove(emergencia);
            emergenciasResueltas.add(emergencia);
            System.out.println("Recursos liberados de la emergencia " + idEmergencia);

            // Intentar asignar recursos a emergencias en cola
            if (!colaEmergencias.isEmpty()) {
                Emergencia siguienteEmergencia = colaEmergencias.remove(0);
                if (asignarRecursosAEmergencia(siguienteEmergencia.getId())) {
                    emergenciasActivas.add(siguienteEmergencia);
                    System.out.println("Emergencia en cola " + siguienteEmergencia.getId() + 
                                     " atendida con recursos liberados.");
                }
            }
        }
    }

    public Map<TipoRecurso, Integer> getLimitesRecursos() {
        return LIMITES_RECURSOS;
    }
}
