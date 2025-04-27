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

        // Logica para crear recursos iniciales

        // Recursos de Bomberos
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.CamionBomberos("CB001", "Estacion Central"));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.CamionBomberos("C002", "Estacion Norte"));
        recursosDisponibles
                .add(new com.jquiguantar.ues.model.resources.BomberosPersonal("BOMP001", "Estacion Central"));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.BomberosPersonal("BOMP002", "Estacion Norte"));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.BomberosPersonal("BOMP003", "Estacion Sur"));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.BomberosPersonal("BOMP004", "Estacion Oeste"));

        // Recursos de Ambulancias
        recursosDisponibles
                .add(new com.jquiguantar.ues.model.resources.AmbulanciaVehiculo("AMB001", "Estacion Central"));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.AmbulanciaVehiculo("AMB002", "Estacion Norte"));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.ParamedicosPersonal("PAR001", "Estacion Sur"));
        recursosDisponibles
                .add(new com.jquiguantar.ues.model.resources.ParamedicosPersonal("PAR002", "Estacion Oeste"));

        System.out.println(
                "Recursos del sistema inicializados con " + recursosDisponibles.size() + " unidades disponibles.");
    }

    // METODO PARA REGISTRAR UNA NUEVA EMERGENCIA
    public void registrarEmergencia(Emergencia emergencia) {
        if (emergencia != null) {
            this.emergenciasActivas.add(emergencia);
            System.out.println("Emergencia Registrada...");
            // Aqui podemos introducir el patron observer, pero lo haremos mas adelante
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
}
