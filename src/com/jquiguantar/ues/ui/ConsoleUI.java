package com.jquiguantar.ues.ui;

import com.jquiguantar.ues.model.emergencies.NivelGravedad;
import com.jquiguantar.ues.model.emergencies.TipoEmergencia;
//Importamos las clases concretas de tipos de emergencia que hemos creado(Incendio,accidenteVehicular,Robo, etc)
import com.jquiguantar.ues.model.emergencies.AccidenteVehicular;
import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.emergencies.Incendio;
import com.jquiguantar.ues.model.emergencies.Robo;
import com.jquiguantar.ues.model.resources.Recursos;
// import com.jquiguantar.ues.model.resources.TipoRecurso;
// import com.jquiguantar.ues.model.emergencies.EstadoEmergencia;
// import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import com.jquiguantar.ues.utils.Ubicacion;
// Importar Estrategias y Singleton
import com.jquiguantar.ues.patterns.singleton.SistemaGestionEmergencia;
import com.jquiguantar.ues.patterns.strategy.EstrategiaPriorizacion;
import com.jquiguantar.ues.patterns.strategy.PrioridadPorAntiguedad;
import com.jquiguantar.ues.patterns.strategy.PrioridadPorCercania;
import com.jquiguantar.ues.patterns.strategy.PrioridadPorGravedad;

import java.io.IOException;
import java.util.Map;
// Clase para manejar la interaccion con el usuario a triaves de consola

public class ConsoleUI {
    public Scanner scanner;

    private List<TipoEmergencia> TiposEmergenciaDisponibles;
    private List<EstrategiaPriorizacion> estrategiasDisponibles; // Lista de estrategias

    // Constantes de colores que funcionan en la consola
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m"; // Color piel
    public static final String YELLOW = "\u001B[33m"; // Color violeta
    public static final String GREEN = "\u001B[32m"; // Color verde claro
    public static final String BLUE = "\u001B[34m"; // Color amarillo
    public static final String BOLD = "\u001B[1m"; // Negrita

    // CONSTRUCTOR

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);

        // Inicializa la lista de tipos de emergencia disponibles
        this.TiposEmergenciaDisponibles = new ArrayList<>();
        this.TiposEmergenciaDisponibles.add(new Incendio());
        this.TiposEmergenciaDisponibles.add(new AccidenteVehicular());
        this.TiposEmergenciaDisponibles.add(new Robo());

        // Inicializa la lista de estrategias disponibles (se mantiene por si se usa internamente)
        this.estrategiasDisponibles = new ArrayList<>();
        this.estrategiasDisponibles.add(new PrioridadPorGravedad());
        this.estrategiasDisponibles.add(new PrioridadPorCercania(new Ubicacion(0, 0)));
        this.estrategiasDisponibles.add(new PrioridadPorAntiguedad());
    }

    public void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("No se pudo limpiar la consola.");
        }
    }

    /**
     * Muestra el Menu principal de Opciones de Usuario (Versión 5 opciones)
     */
    public void mostrarMenuPrincipal() {
        limpiarConsola();
        // Ya no mostramos la estrategia actual aquí
        System.out.println(BOLD + BLUE + "===============================================" + RESET);
        System.out.println(BOLD + BLUE + "    SISTEMA DE GESTIÓN DE EMERGENCIAS URBANAS" + RESET);
        System.out.println(BOLD + BLUE + "===============================================" + RESET);
        System.out.println("1. Registrar Emergencia");
        System.out.println("2. Ver Estado de Recursos");
        System.out.println("3. Atender Emergencia (Automático por Prioridad)"); // Descripción ajustada
        System.out.println("4. Estadísticas del Día");
        System.out.println("5. Finalizar Programa"); // Opción 5 es salir
        System.out.println(BOLD + BLUE + "===============================================" + RESET);
    }

    /**
     * Lee la opcion ingresada por el usuario desde la consola.
     * Implementa validacion basica para asegurar que es un numero
     * 
     * @return La opcion ingresada por el usuario
     */

    public int leerOpcion() {
        // Se utiliza el método auxiliar para leer y validar la opción.
        // El predicado `(opcion) -> true` significa que cualquier entero es aceptado
        // por ahora,
        // la validación específica del rango de opciones se hace en el bucle principal
        // de la aplicación.
        return leerEnteroValidado(
                YELLOW + "Por favor, ingrese el número de la opción deseada: " + RESET,
                "Opción no válida.",
                (opcion) -> true);
    }

    public void cerrarScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

    /**
     * Muestra la lista de tipos de emergencias disponibles y
     * solicita al usuario que ingrese el numero del tipo de emergencia
     * que desean registrar.
     * 
     * @return El TipoEmergencia seleccionado por el usuario
     */
    public TipoEmergencia solicitarTipoEmergencia() {
        limpiarConsola();
        System.out.println(BOLD + YELLOW + "REGISTRAR NUEVA EMERGENCIA" + RESET);
        System.out.println(BOLD + YELLOW + "--------------------------" + RESET);
        System.out.println("Seleccione el tipo de emergencia:");

        for (int i = 0; i < TiposEmergenciaDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + TiposEmergenciaDisponibles.get(i).getNombre());
        }

        // Usamos el método auxiliar para leer la opción, validando que esté en el rango
        // correcto.
        int opcionSeleccionada = leerEnteroValidado(
                YELLOW + "\nIngrese el número del tipo: " + RESET,
                "Opción no válida. Intente nuevamente.",
                (opcion) -> opcion > 0 && opcion <= TiposEmergenciaDisponibles.size());

        return TiposEmergenciaDisponibles.get(opcionSeleccionada - 1);
    }

    /**
     * Lee una coordenada (entero) desde la consola usando el método auxiliar.
     *
     * @param mensaje El mensaje a mostrar para solicitar la coordenada (ej. "X: ")
     * @return La coordenada ingresada.
     */
    private int leerCoordenada(String mensaje) {
        // Utiliza leerEnteroValidado aceptando cualquier entero.
        return leerEnteroValidado(
                mensaje,
                "Entrada no válida.",
                (coord) -> true);
    }

    /**
     * Solicita al usuario la ubucacion de la emergencia (coordenadas)
     * 
     * @return Un objeto Ubicacion con las coordenadas ingresadas
     */
    public Ubicacion solicitarUbicacion() {
        System.out.println("Ingrese la ubicacion de la emergencia (coordenadas X  Y): ");
        // llama a los metodos auxiliares para solicitar las coordenadas
        int x = leerCoordenada("X: ");
        int y = leerCoordenada("Y: ");
        return new Ubicacion(x, y);
    }

    /**
     * Solicita al usuario que ingrese el nivel de gravedad de la emergencia.
     * Valida que el nivel ingresado corresponda a uno de los valores definidos
     * en el enum NivelGravedad.
     * 
     * @return El nivel de gravedad seleccionado por el usuario.
     * @throws IllegalArgumentException si el nivel ingresado no es valido.
     */

    public NivelGravedad solicitarNivelGravedad() {
        System.out.println(BOLD + YELLOW + "\nNIVEL DE GRAVEDAD" + RESET);
        System.out.println(BOLD + YELLOW + "-----------------" + RESET);

        NivelGravedad[] gravedades = NivelGravedad.values();
        for (int i = 0; i < gravedades.length; i++) {
            System.out.println((i + 1) + ". " + gravedades[i]);
        }

        // Usamos el método auxiliar para leer la opción, validando que esté en el rango
        // correcto.
        int opcionSeleccionada = leerEnteroValidado(
                YELLOW + "\nIngrese el número del nivel de gravedad: " + RESET,
                "Opción no válida. Intente nuevamente.",
                (opcion) -> opcion > 0 && opcion <= gravedades.length);

        return gravedades[opcionSeleccionada - 1];
    }

    /**
     * Lee un entero desde la consola, validando la entrada.
     * Repite la solicitud hasta que se ingrese un entero válido que cumpla con el
     * criterio de validación.
     *
     * @param mensajePrompt El mensaje a mostrar al usuario para solicitar la
     *                      entrada.
     * @param mensajeError  El mensaje de error a mostrar si la entrada no es un
     *                      número o no es válida.
     * @param validador     Una función lambda (Predicate) que define el criterio de
     *                      validez del número ingresado.
     * @return El número entero validado.
     */
    private int leerEnteroValidado(String mensajePrompt, String mensajeError,
            java.util.function.Predicate<Integer> validador) {
        int valor = Integer.MIN_VALUE;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensajePrompt);
            try {
                String input = scanner.nextLine();
                valor = Integer.parseInt(input);
                if (validador.test(valor)) {
                    valido = true;
                } else {
                    System.out.println(RED + mensajeError + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Entrada no válida. Por favor, ingrese un número entero." + RESET);
            }
        }
        return valor;
    }

    public void mostrarEstadoRecursos(List<Recursos> disponibles, List<Recursos> ocupados) {
        limpiarConsola();
        System.out.println(BOLD + YELLOW + "ESTADO DE RECURSOS" + RESET);
        System.out.println(BOLD + YELLOW + "------------------" + RESET);

        System.out.println(GREEN + "\nRecursos Disponibles (" + disponibles.size() + "):" + RESET);
        if (disponibles.isEmpty()) {
            System.out.println("No hay recursos disponibles en este momento.");
        } else {
            for (Recursos recurso : disponibles) {
                System.out.println("- ID: " + recurso.getId() +
                        " | Tipo: " + recurso.getTipo() +
                        " | Ubicación: " + recurso.getUbicacionActual());
            }
        }

        System.out.println(RED + "\nRecursos Ocupados (" + ocupados.size() + "):" + RESET);
        if (ocupados.isEmpty()) {
            System.out.println("No hay recursos ocupados en este momento.");
        } else {
            for (Recursos recurso : ocupados) {
                System.out.println("- ID: " + recurso.getId() +
                        " | Tipo: " + recurso.getTipo() +
                        " | Ubicación: " + recurso.getUbicacionActual());
            }
        }
        System.out.println("\nPresione ENTER para volver al menú principal...");
        scanner.nextLine();
    }

    /**
     * Muestra la lista de emergencias activas en el sistema.
     * 
     * Imprime los detalles de cada emergencia activa, incluyendo su ID, tipo,
     * ubicación, nivel de gravedad, estado, y la cantidad de recursos asignados.
     * Si no hay emergencias activas, indica que no hay emergencias registradas
     * en el momento.
     * 
     * La lista se muestra priorizada según la estrategia actual.
     * 
     * @param emergencias La lista de emergencias activas (ya priorizada) a mostrar.
     */

    public void mostrarEmergenciasActivas(List<Emergencia> emergencias) { // Recibe lista ya priorizada
        limpiarConsola();
        System.out.println(BOLD + YELLOW + "EMERGENCIAS ACTIVAS (Priorizadas)" + RESET);
        System.out.println(BOLD + YELLOW + "---------------------------------" + RESET);
        
        if (emergencias.isEmpty()) {
            System.out.println("No hay emergencias activas en este momento.");
        } else {
            for (Emergencia emergencia : emergencias) {
                 // Solo mostramos las PENDIENTES o EN_PROGRESO aquí
                if (emergencia.getEstado() == com.jquiguantar.ues.model.emergencies.EstadoEmergencia.PENDIENTE ||
                    emergencia.getEstado() == com.jquiguantar.ues.model.emergencies.EstadoEmergencia.EN_PROGRESO) {

                    System.out.println("\nID: " + emergencia.getId());
                    System.out.println("Tipo: " + emergencia.getTipo().getNombre());
                    System.out.println("Ubicación: " + emergencia.getUbicacion());
                    System.out.println("Gravedad: " + emergencia.getNivelGravedad());
                    System.out.println("Estado: " + emergencia.getEstado());
                    System.out.println("Recursos Asignados: " + emergencia.getRecursosAsignados().size());
                    System.out.println("Registrada: " + emergencia.getTiempoInicio().format(java.time.format.DateTimeFormatter.ISO_LOCAL_TIME));
                    System.out.println("-------------------");
                }
            }
        }
        // Añadimos pausa al final si es necesario
        // System.out.println("
        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();
    }

    /**
     * Muestra las estrategias de priorización disponibles y permite al usuario seleccionar una.
     * Actualiza la estrategia en SistemaGestionEmergencia.
     * (Actualmente no se llama desde el menú principal)
     */
    public void seleccionarEstrategiaPriorizacion() {
        limpiarConsola();
        System.out.println(BOLD + YELLOW + "CAMBIAR ESTRATEGIA DE PRIORIZACIÓN" + RESET);
        System.out.println(BOLD + YELLOW + "---------------------------------" + RESET);
        System.out.println("Seleccione la nueva estrategia:");

        for (int i = 0; i < estrategiasDisponibles.size(); i++) {
            String nombreEstrategia = "Desconocida";
            // Intentamos obtener un nombre más descriptivo si existe un método
            // (Asumiendo que EstrategiaPriorizacion podría tener un getName() o similar en el futuro,
            // por ahora usamos el nombre de la clase)
            try {
                // Ejemplo: si tuvieras un método getNombre() en la interfaz/clases
                // nombreEstrategia = estrategiasDisponibles.get(i).getNombre(); 
                 nombreEstrategia = estrategiasDisponibles.get(i).getClass().getSimpleName();
            } catch (Exception e) {
                 nombreEstrategia = estrategiasDisponibles.get(i).getClass().getSimpleName();
            }
            System.out.println((i + 1) + ". " + nombreEstrategia);
        }

        int opcionSeleccionada = leerEnteroValidado(
                YELLOW + "\nIngrese el número de la estrategia: " + RESET,
                "Opción no válida. Intente nuevamente.",
                (opcion) -> opcion > 0 && opcion <= estrategiasDisponibles.size()
        );

        EstrategiaPriorizacion nuevaEstrategia = estrategiasDisponibles.get(opcionSeleccionada - 1);
        SistemaGestionEmergencia.getInstance().setEstrategiaPriorizacion(nuevaEstrategia);
        mostrarMensajeExito("Estrategia de priorización actualizada a: " + nuevaEstrategia.getClass().getSimpleName());
    }

    public void mostrarMensajeExito(String mensaje) {
        System.out.println(GREEN + "\n✓ " + mensaje + RESET); // Añadido icono ✓
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    public void mostrarMensajeError(String mensaje) {
        System.out.println(RED + "\n✗ " + mensaje + RESET);
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    // Método para mostrar estadísticas
    public void mostrarEstadisticas(int totalEmergencias, Map<TipoEmergencia, Integer> atendidasPorTipo, Map<TipoEmergencia, Long> tiempoTotalPorTipo) {
        limpiarConsola();
        System.out.println(BOLD + BLUE + "\n--- REPORTE DE ESTADÍSTICAS ---" + RESET);
        System.out.println(YELLOW + "Total de emergencias atendidas (Resueltas): " + totalEmergencias + RESET);

        // Estadisticas por tipo de emergencia
        if (atendidasPorTipo != null && !atendidasPorTipo.isEmpty()) {
            System.out.println(YELLOW + "\n--- Estadísticas por tipo de emergencia ---" + RESET);
            // Usar un conjunto de tipos presentes en las estadísticas para iterar
            for (TipoEmergencia tipo : atendidasPorTipo.keySet()) {
                int atendidas = atendidasPorTipo.getOrDefault(tipo, 0);
                long tiempoTotal = tiempoTotalPorTipo.getOrDefault(tipo, 0L);
                long tiempoPromedioMs = (atendidas > 0) ? tiempoTotal / atendidas : 0;
                double tiempoPromedioSeg = tiempoPromedioMs / 1000.0;

                System.out.println(YELLOW + "   - " + tipo.getNombre() + ":" + RESET);
                System.out.println(YELLOW + "     Atendidas: " + atendidas + RESET);
                System.out.println(YELLOW + "     Tiempo Promedio Respuesta: " + tiempoPromedioMs + " ms (" +
                                   String.format("%.2f", tiempoPromedioSeg) + " segundos)" + RESET);
            }
        } else {
            System.out.println(YELLOW + "No hay emergencias resueltas aún para mostrar estadísticas por tipo." + RESET);
        }
        System.out.println(); // Línea en blanco para separar
        System.out.print(YELLOW + "Presione ENTER para volver al menú principal..." + RESET); 
        scanner.nextLine(); // Esperar que el usuario presione Enter
    }

}
