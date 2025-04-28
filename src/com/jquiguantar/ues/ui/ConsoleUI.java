package com.jquiguantar.ues.ui;

import com.jquiguantar.ues.model.emergencies.NivelGravedad;
import com.jquiguantar.ues.model.emergencies.TipoEmergencia;
//Importamos las clases concretas de tipos de emergencia que hemos creado(Incendio,accidenteVehicular,Robo, etc)
import com.jquiguantar.ues.model.emergencies.AccidenteVehicular;
import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.emergencies.Incendio;
import com.jquiguantar.ues.model.emergencies.Robo;
import com.jquiguantar.ues.model.resources.Recursos;
import com.jquiguantar.ues.model.resources.TipoRecurso;
import com.jquiguantar.ues.model.emergencies.EstadoEmergencia;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

// Clase para manejar la interaccion con el usuario a traves de consola

public class ConsoleUI {
    private Scanner scanner;
    private List<TipoEmergencia> TiposEmergenciaDisponibles;

    // Constantes de colores que funcionan en la consola
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";      // Color piel
    public static final String YELLOW = "\u001B[33m";   // Color violeta
    public static final String GREEN = "\u001B[32m";    // Color verde claro
    public static final String BLUE = "\u001B[34m";     // Color amarillo
    public static final String BOLD = "\u001B[1m";      // Negrita

    // Constantes para el formato
    private static final String BULLET = "→";  // Cambiamos el símbolo de viñeta
    private static final String SEPARATOR = "────────────────────────────────────────";

    // CONSTRUCTOR

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.TiposEmergenciaDisponibles = new ArrayList<>();
        this.TiposEmergenciaDisponibles.add(new Incendio());
        this.TiposEmergenciaDisponibles.add(new AccidenteVehicular());
        this.TiposEmergenciaDisponibles.add(new Robo());
    }

    /**
     * Muestra el Menu principal de Opciones de Usuario
     */
    public void mostrarMenuPrincipal() {
        System.out.println(BOLD + YELLOW + "=== MENÚ PRINCIPAL ===" + RESET);
        System.out.println();
        System.out.println("1. Registrar Emergencia");
        System.out.println("2. Ver Estado de Recursos");
        System.out.println("3. Atender Emergencia");
        System.out.println("4. Mostrar Estadísticas");
        System.out.println("5. Salir");
        System.out.println();
        System.out.print(YELLOW + "Seleccione una opción: " + RESET);
    }

    /**
     * Lee la opcion ingresada por el usuario desde la consola.
     * Implementa validacion basica para asegurar que es un numero
     * 
     * @return La opcion ingresada por el usuario
     */

    public int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
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
        System.out.println(BOLD + YELLOW + "=== REGISTRO DE NUEVA EMERGENCIA ===" + RESET);
        System.out.println();
        System.out.println(YELLOW + "Seleccione el tipo de emergencia:" + RESET);
        System.out.println();
        System.out.println("1. Incendio");
        System.out.println("2. Accidente Vehicular");
        System.out.println("3. Robo");
        System.out.println();
        System.out.print(YELLOW + "Ingrese el número del tipo: " + RESET);

        TipoEmergencia tipoSeleccionado = null;
        while (tipoSeleccionado == null) {
            try {
                String input = scanner.nextLine();
                int opcion = Integer.parseInt(input);
                if (opcion > 0 && opcion <= TiposEmergenciaDisponibles.size()) {
                    tipoSeleccionado = TiposEmergenciaDisponibles.get(opcion - 1);
                } else {
                    System.out.println(RED + "Opción no válida. Intente nuevamente." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Opción no válida. Intente nuevamente." + RESET);
            }
        }
        return tipoSeleccionado;
    }

    /**
     * Solicita al usuario que ingrese la ubicacion de la emergencia
     * 
     * @return La ubicacion como String
     */
    public String solicitarUbicacion() {
        System.out.print(YELLOW + "Ingrese la ubicación de la emergencia: " + RESET);
        return scanner.nextLine();
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
        System.out.println(YELLOW + "Niveles de gravedad disponibles:" + RESET);
        System.out.println();
        System.out.println("1. BAJA");
        System.out.println("2. MEDIA");
        System.out.println("3. ALTA");
        System.out.println();
        System.out.print(YELLOW + "Ingrese el número del nivel de gravedad: " + RESET);

        NivelGravedad gravedadSeleccionada = null;
        while (gravedadSeleccionada == null) {
            try {
                String input = scanner.nextLine();
                int opcion = Integer.parseInt(input);
                if (opcion > 0 && opcion <= NivelGravedad.values().length) {
                    gravedadSeleccionada = NivelGravedad.values()[opcion - 1];
                } else {
                    System.out.println(RED + "Opción no válida. Intente nuevamente." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Opción no válida. Intente nuevamente." + RESET);
            }
        }
        return gravedadSeleccionada;
    }

    public void mostrarEstadoRecursos(List<Recursos> disponibles, List<Recursos> ocupados) {
        System.out.println(BOLD + YELLOW + "=== ESTADO ACTUAL DE RECURSOS ===" + RESET);
        System.out.println();
        System.out.println(GREEN + "Recursos Disponibles (" + disponibles.size() + "):" + RESET);
        System.out.println();
        System.out.println("ID      | TIPO               | UBICACIÓN");
        System.out.println("────────|────────────────────|───────────");

        if (disponibles.isEmpty()) {
            System.out.println("- No hay recursos disponibles");
        } else {
            for (Recursos recurso : disponibles) {
                System.out.println(String.format("%-7s | %-18s | %s", 
                    recurso.getId(), 
                    recurso.getTipo(), 
                    recurso.getUbicacionActual()));
            }
        }

        System.out.println();
        System.out.println(RED + "Recursos Ocupados (" + ocupados.size() + "):" + RESET);

        if (ocupados.isEmpty()) {
            System.out.println("- No hay recursos ocupados");
        } else {
            for (Recursos recurso : ocupados) {
                System.out.println(String.format("%-7s | %-18s | %s", 
                    recurso.getId(), 
                    recurso.getTipo(), 
                    recurso.getUbicacionActual()));
            }
        }
    }

    /**
     * Muestra la lista de emergencias activas en el sistema.
     * 
     * Imprime los detalles de cada emergencia activa, incluyendo su ID, tipo,
     * ubicación, nivel de gravedad, estado, y la cantidad de recursos asignados.
     * Si no hay emergencias activas, indica que no hay emergencias registradas
     * en el momento.
     * 
     * @param emergencias La lista de emergencias activas a mostrar.
     */

    public void mostrarEmergenciasActivas(List<Emergencia> emergencias) {
        System.out.println(BOLD + YELLOW + "=== EMERGENCIAS ACTIVAS ===" + RESET);
        System.out.println();

        if (emergencias.isEmpty()) {
            System.out.println("No hay emergencias registradas en este momento.");
        } else {
            for (Emergencia emergencia : emergencias) {
                System.out.println(BOLD + BLUE + "Emergencia #" + emergencia.getId() + RESET);
                System.out.println("→ Tipo: " + emergencia.getTipo().getNombre());
                System.out.println("→ Ubicación: " + emergencia.getUbicacion());
                System.out.println("→ Nivel de Gravedad: " + emergencia.getNivelGravedad());
                System.out.println("→ Estado: " + emergencia.getEstado());
                System.out.println("→ Recursos Asignados: " + emergencia.getRecursosAsignados().size());
                System.out.println();
            }
        }
    }

    /**
     * Solicita al usuario que ingrese el ID de una emergencia activa
     * para atender.
     * 
     * @return El ID de la emergencia a atender como String.
     */
    public String solicitarIdEmergenciaAAtender() {
        System.out.println();
        System.out.print(YELLOW + "Ingrese el ID de la emergencia a atender: " + RESET);
        return scanner.nextLine();
    }

    // Añadir método para esperar entrada del usuario
    public void esperarEntradaUsuario() {
        System.out.println();
        System.out.println(YELLOW + "Presione Enter para continuar..." + RESET);
        scanner.nextLine();
    }

    public void mostrarMensajeExito(String mensaje) {
        System.out.println(GREEN + mensaje + RESET);
    }

    public void mostrarMensajeError(String mensaje) {
        System.out.println(RED + mensaje + RESET);
    }

    public void mostrarEstadisticas() {
        System.out.println(YELLOW + "--- Estadísticas del Día ---" + RESET);
        // Aquí irían las estadísticas reales
    }

    // Mostrar estadisticas...

}
