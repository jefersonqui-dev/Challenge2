package com.jquiguantar.ues.patterns.singleton;

//al Implementar el patrón Singleton en esta clase 
//sabemos que esto garantizara que solo se pueda crear 
//una única instancia de ella durante toda la ejecución del programa.
import com.jquiguantar.ues.model.emergencies.Emergencia;

import com.jquiguantar.ues.model.emergencies.EstadoEmergencia;
import com.jquiguantar.ues.model.emergencies.TipoEmergencia;
import com.jquiguantar.ues.model.resources.TipoRecurso;
import com.jquiguantar.ues.patterns.observer.ObservadorEmergencia;
import com.jquiguantar.ues.patterns.strategy.EstrategiaPriorizacion;
// import com.jquiguantar.ues.patterns.strategy.PrioridadPorGravedad;
import com.jquiguantar.ues.patterns.strategy.PrioridadPorCercania;
import com.jquiguantar.ues.services.GestionEmergencia;

import com.jquiguantar.ues.model.resources.Recursos;
import com.jquiguantar.ues.services.AmbulanciaService;
import com.jquiguantar.ues.services.BomberoService;
import com.jquiguantar.ues.services.PoliciaService;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;//Necesario para usar lambdas con streams
import com.jquiguantar.ues.utils.Ubicacion;
import com.jquiguantar.ues.utils.MapaUrbano;
import java.util.Comparator;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;


public class SistemaGestionEmergencia {
    // Constantes de colores que funcionan en la consola
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m"; // Color piel
    public static final String YELLOW = "\u001B[33m"; // Color violeta
    public static final String GREEN = "\u001B[32m"; // Color verde claro
    public static final String BLUE = "\u001B[34m"; // Color amarillo
    public static final String BOLD = "\u001B[1m"; // Negrita

    // Constantes para tiempos de espera (en milisegundos)
    private static final int TIEMPO_BUSQUEDA = 1500;
    private static final int TIEMPO_PROCESAMIENTO = 2000;
    private static final int TIEMPO_ASIGNACION = 1000;

    /**
     * Simula un tiempo de espera con puntos de progreso
     * 
     * @param tiempoEspera Tiempo total de espera en milisegundos
     * @param mensaje      Mensaje a mostrar durante la espera
     */
    private void simularProcesamiento(int tiempoEspera, String mensaje) {
        System.out.print(mensaje);
        //int puntos = 0;
        int intervalo = tiempoEspera / 4; // Dividir el tiempo en 4 partes

        try {
            for (int i = 0; i < 4; i++) {
                Thread.sleep(intervalo);
                System.out.print(".");
                //puntos++;
            }
            System.out.println("- ");
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

    // Nuevo atributo : Estrategia de priorizacion actual
    private EstrategiaPriorizacion estrategiaPriorizacionActual;

    private List<ObservadorEmergencia> observadores; // Lista de observadores


    //ATRIBUTOS PARA ESTADISTICAS
    private int totalEmergenciasAtendidas;
    private Map<TipoEmergencia,Integer> emergenciasAtendidasPorTipo; //Contador por tipo
    private Map<TipoEmergencia,Long> tiempoTotalRespuestaPorTipoMilis; //Acumulador de tiempo de respuesta por tipo
    

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

        // Inicializa la estrategia de priorización por defecto
        this.estrategiaPriorizacionActual = new PrioridadPorCercania(new Ubicacion(0, 0));

        this.observadores = new ArrayList<>(); // Inicializa la lista de observadores

        //Inicializa los atributos de estadisticas
        this.totalEmergenciasAtendidas = 0;
        this.emergenciasAtendidasPorTipo = new HashMap<>();
        this.tiempoTotalRespuestaPorTipoMilis = new HashMap<>();

        // LLama a un metodo de inicializacion de recursos
        inicializarRecursos();
        System.out.println(BOLD + BLUE + "Sistema de Emergencias Urbanas Inicializado..." + RESET);

        // Registrar observadores(creamos uno a continuacion)
        agregarObservador(new com.jquiguantar.ues.patterns.observer.ConsoleNotificationObserver());
        //remover observador
        //notificarObservadores(null);
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

    // METODOS PARA GESTION DE OBSERVADORES
    /**
     * Agrega un observador a la lista de observadores.
     * @param obs El observador a agregar.
     */
    public void agregarObservador(ObservadorEmergencia obs) {
        if (obs != null && !this.observadores.contains(obs)) {
            this.observadores.add(obs);
        }
    }
    /**
     * Elimina un observador de la lista de observadores.
     * @param obs El observador a eliminar.
     */
    public void removerObservador(ObservadorEmergencia obs) {
        if (obs != null) {
            this.observadores.remove(obs);
        }
    }
    
    /**
     * Notifica a todos los observadores registrados sobre una emergencia.
     * Este metodo es llamado internamente cuando ocurre un evento relevante.
     * @param emergencia La emergencia sobre la que se notifica.
     */
    public void notificarObservadores(Emergencia emergencia) {
        System.out.println(BOLD + BLUE + "Notificando observadores sobre emergencia ID: " + emergencia.getId() + RESET);

        for (ObservadorEmergencia obs : this.observadores) {
            obs.actualizar(emergencia);//llama al metodo actualizar de cada observador
        }
        System.out.println(BOLD + BLUE + "Notificación completada." + RESET);
    }

    // --- METODOS PARA GESTIONAR EL ESTADO DEL SISTEMA ---

    // Metodo para inicializar los recursos del sitema (crear instancias de recurso)
    private void inicializarRecursos() {
        this.recursosDisponibles.clear();
        this.recursosOcupados.clear();
        //***Crear instancias de recursos concretos usando Ubicacion */

        // Recursos de Bomberos
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.CamionBomberos("CB001", new Ubicacion(50, 50)));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.CamionBomberos("C002", new Ubicacion(10, 90)));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.BomberosPersonal("BOMP001", new Ubicacion(50, 50)));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.BomberosPersonal("BOMP002", new Ubicacion(10, 90)));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.BomberosPersonal("BOMP003", new Ubicacion(10, 90)));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.BomberosPersonal("BOMP004", new Ubicacion(10, 90)));

        // Recursos de Ambulancia
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.AmbulanciaVehiculo("AMB001", new Ubicacion(50, 50)));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.AmbulanciaVehiculo("AMB002", new Ubicacion(10, 90)));

        // Recursos de Policia
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.Patrulla("UP001", new Ubicacion(50, 50)));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.Patrulla("UP002", new Ubicacion(10, 90)));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.Patrulla("UP003", new Ubicacion(10, 90)));
        recursosDisponibles.add(new com.jquiguantar.ues.model.resources.Patrulla("UP004", new Ubicacion(10, 90)));
        
        // System.out.println("Recursos Inicializados...");
    }

    // METODO PARA REGISTRAR UNA NUEVA EMERGENCIA
    /**
     * Registra una nueva emergencia en el sistema.
     * @param emergencia La emergencia a registrar.
     */
    public void registrarEmergencia(Emergencia emergencia) {
        if (emergencia != null) {
            this.emergenciasActivas.add(emergencia);
            System.out.println(GREEN + 
            "Emergencia registrada con ID: " + emergencia.getId() + 
            " (" + emergencia.getTipo().getNombre() + 
            " en " + emergencia.getUbicacion() + 
            ", Gravedad: " + emergencia.getNivelGravedad() + ")" + RESET);
            //Aqui disparamos la notificacion a los observadores
            notificarObservadores(emergencia);
        }
    }
    // Metodos para obtener las listas

    public List<Emergencia> getEmergenciasActivas() {
        return estrategiaPriorizacionActual.priorizar(new ArrayList<>(this.emergenciasActivas));
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

    // Metodo para cambiar la estrategia de priorizacion
    public void setEstrategiaPriorizacion(EstrategiaPriorizacion estrategia) {
        if (estrategia != null) {
            this.estrategiaPriorizacionActual = estrategia;
            System.out.println( GREEN + "Estrategia de priorizacion cambiada a: " + estrategia.getClass().getSimpleName() + RESET);

        }
    }

    // METODO PARA ASIGNAR RECURSOS
    /**
     * Asigna recursos a una emergencia específica, priorizando por cercania.
     * 
     * @param idEmergencia El ID de la emergencia a asignar recursos.
     * @return true si los recursos se asignaron  recursos (completa o parcialmente), false en caso
     *         contrario.   
     */
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
                " (" + emergencia.getNivelGravedad() + ")" + 
                " en " + emergencia.getUbicacion() + RESET); //ahora mostramos la ubicacion de la emergencia



        // 2. Obtener recursos necesarios
        simularProcesamiento(TIEMPO_PROCESAMIENTO, YELLOW + "  Calculando recursos necesarios" + RESET);
        Map<TipoRecurso, Integer> recursosNecesarios = emergencia.getTipo()
                .calcularRecursosInicialesNecesarios(emergencia.getNivelGravedad());
        System.out.println(YELLOW + "  Recursos requeridos calculados." + RESET);

        // 3. Asignar recursos por tipo
        System.out.println(BOLD + BLUE + "\n--- Detalle de Asignación ---" + RESET);
        boolean asignacionExitosa = asignarRecursosPorTipo(emergencia, recursosNecesarios);

        // 4. Actualizar estado de la emergencia y finalizar
        if (asignacionExitosa && emergencia.getRecursosAsignados().size() >= calcularTotalRecursosNecesarios(recursosNecesarios)) {
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
    private int calcularTotalRecursosNecesarios(Map<TipoRecurso, Integer> recursosNecesarios) {
        return recursosNecesarios.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * Valida y busca una emergencia por su ID. Imprime error si no es válida.
     * 
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
     * 
     * @param emergencia         Emergencia a la que asignar recursos
     * @param recursosNecesarios Mapa de tipos de recursos y cantidades necesarias
     * @return true si se asignaron recursos exitosamente (al menos uno)
     */
    private boolean asignarRecursosPorTipo(Emergencia emergencia, Map<TipoRecurso, Integer> recursosNecesarios) {
        boolean asignacionGeneralExitosa = false;
        //boolean recursosSuficientes = true;

        for (Map.Entry<TipoRecurso, Integer> entry : recursosNecesarios.entrySet()) {
            TipoRecurso tipoNecesario = entry.getKey();
            int cantidadNecesaria = entry.getValue();

            System.out.println(YELLOW + "\n  Tipo: " + tipoNecesario + " | Necesarios: " + cantidadNecesaria + RESET);

            // Obtener recursos disponibles del tipo requerido y ordenarlos por cercania
            simularProcesamiento(TIEMPO_BUSQUEDA, YELLOW + "    Buscando y Priorizando por cercania" + RESET);
            List<Recursos> recursosDisponiblesDelTipo = recursosDisponibles.stream()
                    .filter(r -> r.getTipo() == tipoNecesario)
                    //Ordenamos por cercania USANDO LAMBDAS
                    .sorted(Comparator.comparingInt(r -> MapaUrbano.CalcularDistanciaManhattan(r.getUbicacionActual(),emergencia.getUbicacion())))
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
                    
                }
            } else {
                System.out.println(RED + "    No se asignó ninguno (Insuficientes o no disponibles del tipo requerido)." + RESET);
                
            }
        }
       
        return asignacionGeneralExitosa;
    }

    /**
     * Asigna recursos disponibles a una emergencia (sin mensajes de consola)
     * 
     * @param emergencia          Emergencia a la que asignar recursos
     * @param recursosDisponibles Lista de recursos disponibles
     * @param cantidadNecesaria   Cantidad de recursos necesarios
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

    private void liberarRecursosEmergencia(Emergencia emergencia) {
        if(emergencia != null && emergencia.getRecursosAsignados() != null){
            for(Recursos recurso : emergencia.getRecursosAsignados()){
                //Aseguramos que el recurso estaba ocupado y moverlo
                boolean removido = recursosOcupados.remove(recurso);
                if(removido){
                    recursosDisponibles.add(recurso);
                    recurso.setDisponible(true);
                    //vamos a simular que el recurso regresa a la ubicacion de la emergencia resuelta
                    recurso.setUbicacionActual(emergencia.getUbicacion());
                    System.out.println("    Recurso ID " + recurso.getId() + " liberado."); 
                }
            }
           // emergencia.getRecursosAsignados().clear(); se debe quitar para usar en estadisticas
        }
    }
    public void resolverEmergenciasEnProgreso(){
        System.out.println(YELLOW + "\n--- Simulando avance y resolución de emergencias en progreso ---" + RESET);
        List<Emergencia> resueltasEnEsteCiclo = new ArrayList<>();

        //iterar sobre una copia de la lista para poder modificarla

        for(Emergencia emergencia : new ArrayList<>(this.emergenciasActivas)){
            //simplificacion: cualquier emergencia en progreso se resuelve en este ciclo

            if(emergencia.getEstado() == EstadoEmergencia.EN_PROGRESO){
                //marca como resuelta
                emergencia.setEstado(EstadoEmergencia.RESUELTA);
                
                //mover recursos asignados devuelta a disponibles
                liberarRecursosEmergencia(emergencia);
                acumularEstadisticas(emergencia);

                //mover emergencia de activas a resuelta
                resueltasEnEsteCiclo.add(emergencia);
                System.out.println(GREEN + "Emergencia ID " + emergencia.getId() + " resuelta." + RESET);

                //notificamos a los observadores   
                notificarObservadores(emergencia);
                

            }
        }
        //mover las emergencias resueltas del ciclo de activas a resueltas
        emergenciasActivas.removeAll(resueltasEnEsteCiclo);
        emergenciasResueltas.addAll(resueltasEnEsteCiclo);
        System.out.println(YELLOW + "--- Fin de la simulación de resolución ---" + RESET);
    }
    //METODO PARA ACUMULAR ESTADISTICAS
    /**
     * Acumula estadísticas de emergencias resueltas.
     * es llamado desde resolverEmergenciasEnProgreso
     * @param emergencia La emergencia resuelta cuyas estadísticas se acumulan.
     */
    private void acumularEstadisticas(Emergencia emergencia){
        if(emergencia.getEstado() == EstadoEmergencia.RESUELTA && emergencia.getTiempoFin() != null){
            TipoEmergencia tipo = emergencia.getTipo();
            long duracionMillis = ChronoUnit.MILLIS.between(emergencia.getTiempoInicio(), emergencia.getTiempoFin());
            //incrementar contador total
            totalEmergenciasAtendidas++;

            //actualizar contador por tipo
            emergenciasAtendidasPorTipo.put(tipo, emergenciasAtendidasPorTipo.getOrDefault(tipo,0) + 1);
            //acumular tiempo total de respuesta
            tiempoTotalRespuestaPorTipoMilis.put(tipo, tiempoTotalRespuestaPorTipoMilis.getOrDefault(tipo,0L) + duracionMillis); 
            System.out.println(GREEN +" Estadisticas acumuladas para emergencia ID: " + emergencia.getId() + "(Duracion: " + duracionMillis + "ms)." + RESET);   
        }
        
    }
    public void generarEstadisticas(){
        System.out.println(BOLD + BLUE + "\n--- REPORTE DE ESTADÍSTICAS ---" + RESET);
        System.out.println(YELLOW + "Total de emergencias atendidas: (Resueltas/Cerradas)" + totalEmergenciasAtendidas + RESET);

        //Estadisticas por tipo de emergencia
        if(!emergenciasAtendidasPorTipo.isEmpty()){
            System.out.println(YELLOW + "\n--- Estadisticas por tipo de emergencia ---" + RESET);
            for(Map.Entry<TipoEmergencia,Integer> entry : emergenciasAtendidasPorTipo.entrySet()){
                TipoEmergencia tipo = entry.getKey();
                int atendidas = entry.getValue();
                long tiempoTotal = tiempoTotalRespuestaPorTipoMilis.getOrDefault(tipo,0L);
                long tiempoPromedio = (atendidas > 0) ? tiempoTotal / atendidas : 0;
                System.out.println(YELLOW + "   - " + tipo.getNombre() + ":" + RESET);
                System.out.println(YELLOW + "     Atendidas: " + atendidas + RESET);
                System.out.println(YELLOW + "     Tiempo Promedio Respuesta: " + tiempoPromedio + " ms("+ 
                String.format("%.2f",(tiempoPromedio/1000.0)) + " segundos)" + RESET); //Formato a 2 decimaes
            }
        }else{
            System.out.println(YELLOW + "No hay emergencias resueltas aún para mostrar estadísticas por tipo." + RESET);
        } 
        //Estadisticas de recursos
        System.out.println(YELLOW + "\n Estado Final de Recursos" + RESET);
        System.out.println(YELLOW + "Recursos Disponibles: " + recursosDisponibles.size() + RESET);
        System.out.println(YELLOW + "Recursos Ocupados: " + recursosOcupados.size() + RESET);
        System.out.println(BOLD + BLUE + "--------------------------------------" + RESET);
    }
}
