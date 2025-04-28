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
    // Constantes de colores que funcionan en la consola
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";      // Color piel
    public static final String YELLOW = "\u001B[33m";   // Color violeta
    public static final String GREEN = "\u001B[32m";    // Color verde claro
    public static final String BLUE = "\u001B[34m";     // Color amarillo
    public static final String BOLD = "\u001B[1m";      // Negrita

    // Constantes para tiempos de espera (en milisegundos)
    private static final int TIEMPO_BUSQUEDA = 1500;
    private static final int TIEMPO_PROCESAMIENTO = 2000;
    private static final int TIEMPO_ASIGNACION = 1000;

    /**
     * Simula un tiempo de espera con puntos de progreso
     * @param tiempoEspera Tiempo total de espera en milisegundos
     * @param mensaje Mensaje a mostrar durante la espera
     */
    private void simularProcesamiento(int tiempoEspera, String mensaje) {
        System.out.print(mensaje);
        int puntos = 0;
        int intervalo = tiempoEspera / 4; // Dividir el tiempo en 4 partes

        try {
            for (int i = 0; i < 4; i++) {
                Thread.sleep(intervalo);
                System.out.print(".");
                puntos++;
            }
            System.out.println(" ✓");
        } catch (InterruptedException e) {
            System.out.println("\n" + RED + "Error en el procesamiento" + RESET);
        }
    }

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
        System.out.println(BOLD + BLUE + "Sistema de Emergencias Urbanas Inicializado..." + RESET);

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

        // System.out.println("Recursos Inicializados...");
    }

    // METODO PARA REGISTRAR UNA NUEVA EMERGENCIA
    public void registrarEmergencia(Emergencia emergencia) {
        if (emergencia != null) {
            this.emergenciasActivas.add(emergencia);
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

    // METODO PARA ASIGNAR RECURSOS
    public boolean asignarRecursosAEmergencia(String idEmergencia) {
        System.out.println(BOLD + BLUE + "\n--- INICIO ASIGNACIÓN DE RECURSOS --- ID: " + idEmergencia + RESET);

        // 1. Validar y obtener la emergencia
        Emergencia emergencia = validarYBuscarEmergencia(idEmergencia);
        if (emergencia == null) {
            // El mensaje de error ya se mostró en validarYBuscarEmergencia
            System.out.println(RED + "--- Fin del Proceso: Emergencia no válida --- " + RESET);
            return false;
        }
        // Mostrar detalles solo si es válida
        System.out.println(GREEN + "  Emergencia válida: " + emergencia.getTipo().getNombre() + 
                         " (" + emergencia.getNivelGravedad() + ")" + RESET);

        // 2. Obtener recursos necesarios
        simularProcesamiento(TIEMPO_PROCESAMIENTO, YELLOW + "  Calculando recursos necesarios" + RESET);
        Map<TipoRecurso, Integer> recursosNecesarios = emergencia.getTipo()
                .calcularRecursosInicialesNecesarios(emergencia.getNivelGravedad());
        System.out.println(YELLOW + "  Recursos requeridos calculados." + RESET);

        // 3. Asignar recursos por tipo
        System.out.println(BOLD + BLUE + "\n--- Detalle de Asignación ---" + RESET);
        boolean asignacionExitosa = asignarRecursosPorTipo(emergencia, recursosNecesarios);

        // 4. Actualizar estado de la emergencia y finalizar
        if (asignacionExitosa) {
            simularProcesamiento(TIEMPO_ASIGNACION, YELLOW + "  Actualizando estado de la emergencia" + RESET);
            emergencia.setEstado(EstadoEmergencia.EN_PROGRESO);
            System.out.println(GREEN + "  Recursos asignados. Emergencia ID " + emergencia.getId() + 
                             " ahora está " + emergencia.getEstado() + RESET);
        } else {
            System.out.println(RED + "  Recursos insuficientes para atender completamente la emergencia ID " + 
                            emergencia.getId() + RESET);
        }
        System.out.println(BOLD + BLUE + "--- Fin del Proceso de Asignación --- " + RESET);

        return asignacionExitosa;
    }

    /**
     * Valida y busca una emergencia por su ID. Imprime error si no es válida.
     * @param idEmergencia ID de la emergencia a buscar
     * @return La emergencia encontrada o null si no es válida
     */
    private Emergencia validarYBuscarEmergencia(String idEmergencia) {
        // Ya no simulamos proceso aquí, se valida directamente
        Optional<Emergencia> emergenciaOpt = emergenciasActivas.stream()
                .filter(e -> e.getId().equals(idEmergencia))
                .findFirst();

        if (!emergenciaOpt.isPresent()) {
            System.out.println(RED + "  Error: Emergencia con ID: " + idEmergencia + 
                            " no encontrada." + RESET);
            return null;
        }

        Emergencia emergencia = emergenciaOpt.get();
        if (emergencia.getEstado() != EstadoEmergencia.PENDIENTE) {
            System.out.println(RED + "  Error: Emergencia con ID: " + idEmergencia + 
                            " no está PENDIENTE. Estado actual: " + 
                            emergencia.getEstado() + RESET);
            return null;
        }
        // Si es válida, no imprimimos nada aquí, solo devolvemos el objeto
        return emergencia;
    }

    /**
     * Asigna recursos por tipo a una emergencia
     * @param emergencia Emergencia a la que asignar recursos
     * @param recursosNecesarios Mapa de tipos de recursos y cantidades necesarias
     * @return true si se asignaron recursos exitosamente (al menos uno)
     */
    private boolean asignarRecursosPorTipo(Emergencia emergencia, 
                                         Map<TipoRecurso, Integer> recursosNecesarios) {
        boolean asignacionGeneralExitosa = false;
        boolean recursosSuficientes = true;

        for (Map.Entry<TipoRecurso, Integer> entry : recursosNecesarios.entrySet()) {
            TipoRecurso tipoNecesario = entry.getKey();
            int cantidadNecesaria = entry.getValue();
            
            System.out.println(YELLOW + "\n  Tipo: " + tipoNecesario + " | Necesarios: " + cantidadNecesaria + RESET);

            // Obtener recursos disponibles del tipo requerido
            simularProcesamiento(TIEMPO_BUSQUEDA, YELLOW + "    Buscando disponibles" + RESET);
            List<Recursos> recursosDisponiblesDelTipo = recursosDisponibles.stream()
                    .filter(r -> r.getTipo() == tipoNecesario)
                    .collect(Collectors.toList());
            System.out.println(YELLOW + "    Encontrados disponibles: " + recursosDisponiblesDelTipo.size() + RESET);

            // Asignar recursos disponibles
            simularProcesamiento(TIEMPO_ASIGNACION, YELLOW + "    Intentando asignar" + RESET);
            int cantidadAsignada = asignarRecursosDisponibles(emergencia, 
                                                            recursosDisponiblesDelTipo, 
                                                            cantidadNecesaria);

            if (cantidadAsignada > 0) {
                System.out.println(GREEN + "    Asignados: " + cantidadAsignada + RESET);
                asignacionGeneralExitosa = true; // Marcamos como exitosa si al menos un recurso se asignó
                if (cantidadAsignada < cantidadNecesaria) {
                    System.out.println(RED + "    Faltaron: " + (cantidadNecesaria - cantidadAsignada) + RESET);
                    recursosSuficientes = false; // Marcamos que faltaron recursos
                }
            } else {
                System.out.println(RED + "    No se asignó ninguno (Insuficientes)." + RESET);
                recursosSuficientes = false; // Marcamos que faltaron recursos
            }
        }
        // Devolvemos true solo si se asignaron TODOS los recursos necesarios
        // O podrías cambiarlo a devolver asignacionGeneralExitosa si quieres que EN_PROGRESO
        // se active con asignación parcial.
        return asignacionGeneralExitosa && recursosSuficientes; 
    }

    /**
     * Asigna recursos disponibles a una emergencia (sin mensajes de consola)
     * @param emergencia Emergencia a la que asignar recursos
     * @param recursosDisponibles Lista de recursos disponibles
     * @param cantidadNecesaria Cantidad de recursos necesarios
     * @return Cantidad de recursos asignados
     */
    private int asignarRecursosDisponibles(Emergencia emergencia, 
                                         List<Recursos> recursosDisponibles, 
                                         int cantidadNecesaria) {
        int cantidadAsignada = 0;

        // Tomar solo los necesarios o los disponibles, lo que sea menor
        int limiteAsignacion = Math.min(cantidadNecesaria, recursosDisponibles.size());

        List<Recursos> aAsignar = new ArrayList<>(recursosDisponibles.subList(0, limiteAsignacion));

        for (Recursos recurso : aAsignar) {
            // Mover recurso de disponibles a ocupados
            this.recursosDisponibles.remove(recurso);
            this.recursosOcupados.add(recurso);
            recurso.setDisponible(false);
            
            // Asignar recurso a la emergencia
            emergencia.agregarRecursoAsignado(recurso);
            cantidadAsignada++;
        }

        return cantidadAsignada;
    }
}
