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

        // Inicializa la lista de tipos de emergencia disponibles
        this.TiposEmergenciaDisponibles = new ArrayList<>();
        this.TiposEmergenciaDisponibles.add(new Incendio());
        this.TiposEmergenciaDisponibles.add(new AccidenteVehicular());
        this.TiposEmergenciaDisponibles.add(new Robo());
    }

    /**
     * Muestra el Menu principal de Opciones de Usuario
     */
    public void mostrarMenuPrincipal() {
        // Posicionar el cursor para el menú principal
        System.out.print("\033[5;3H"); // Mover a la columna 3, fila 5 (después de la línea divisoria)
        
        System.out.println("Seleccione una opción:");
        System.out.print("\033[6;3H");
        System.out.println("1. Registrar Emergencia");
        System.out.print("\033[7;3H");
        System.out.println("2. Ver Estado de Recursos");
        System.out.print("\033[8;3H");
        System.out.println("3. Atender Emergencia");
        System.out.print("\033[9;3H");
        System.out.println("4. Mostrar Estadísticas");
        System.out.print("\033[10;3H");
        System.out.println("5. Salir");
        System.out.print("\033[12;3H"); // Dejar un espacio después de la última opción
        System.out.println("─────────────────────────────");
        System.out.print("\033[13;3H");
        System.out.print(YELLOW + "Por favor, ingrese el número de la opción deseada: " + RESET);
    }

    /**
     * Lee la opcion ingresada por el usuario desde la consola.
     * Implementa validacion basica para asegurar que es un numero
     * 
     * @return La opcion ingresada por el usuario
     */

    public int leerOpcion() {
        int opcion = -1;
        try {
            String input = scanner.nextLine();
            opcion = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(RED + "Opción no válida. Intente nuevamente." + RESET);
        }
        return opcion;
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
        System.out.print("\033[8;2H"); // Posicionar cursor
        System.out.println(YELLOW + "Seleccione el tipo de emergencia: " + RESET);
        System.out.println(SEPARATOR);
        
        for (int i = 0; i < TiposEmergenciaDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + TiposEmergenciaDisponibles.get(i).getNombre());
        }

        TipoEmergencia tipoSeleccionado = null;
        while (tipoSeleccionado == null) {
            System.out.print(YELLOW + "\nIngrese el numero del tipo: " + RESET);
            try {
                String input = scanner.nextLine();
                int opcion = Integer.parseInt(input);
                if (opcion > 0 && opcion <= TiposEmergenciaDisponibles.size()) {
                    tipoSeleccionado = TiposEmergenciaDisponibles.get(opcion - 1);
                } else {
                    System.out.println(RED + "Opcion no valida. Intente nuevamente." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Opcion no valida. Intente nuevamente." + RESET);
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
        System.out.print("\033[8;2H"); // Posicionar cursor
        System.out.print(YELLOW + "Ingrese la ubicacion de la emergencia: " + RESET);
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
        System.out.print("\033[8;2H"); // Posicionar cursor
        System.out.println(YELLOW + "Niveles de gravedad disponibles:" + RESET);
        System.out.println(SEPARATOR);
        
        NivelGravedad[] gravedades = NivelGravedad.values();
        for (int i = 0; i < gravedades.length; i++) {
            System.out.println((i + 1) + ". " + gravedades[i]);
        }

        NivelGravedad gravedadSeleccionada = null;
        while (gravedadSeleccionada == null) {
            System.out.print(YELLOW + "\nIngrese el numero del nivel de gravedad: " + RESET);
            try {
                String input = scanner.nextLine();
                int opcion = Integer.parseInt(input);
                if (opcion > 0 && opcion <= gravedades.length) {
                    gravedadSeleccionada = gravedades[opcion - 1];
                } else {
                    System.out.println(RED + "Opcion no valida. Intente nuevamente." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Opcion no valida. Intente nuevamente." + RESET);
            }
        }
        return gravedadSeleccionada;
    }

    public void mostrarEstadoRecursos(List<Recursos> disponibles, List<Recursos> ocupados) {
        System.out.print("\033[8;2H"); // Posicionar cursor
        System.out.println(BOLD + YELLOW + "ESTADO ACTUAL DE RECURSOS" + RESET);
        System.out.println(SEPARATOR);

        System.out.println(GREEN + "\nRecursos Disponibles (" + disponibles.size() + "):" + RESET);
        if (disponibles.isEmpty()) {
            System.out.println("No hay recursos disponibles en este momento.");
        } else {
            for (Recursos recurso : disponibles) {
                System.out.println(BULLET + " ID: " + recurso.getId() + 
                                 " | Tipo: " + recurso.getTipo() + 
                                 " | Ubicacion: " + recurso.getUbicacionActual());
            }
        }

        System.out.println(RED + "\nRecursos Ocupados (" + ocupados.size() + "):" + RESET);
        if (ocupados.isEmpty()) {
            System.out.println("No hay recursos ocupados en este momento.");
        } else {
            for (Recursos recurso : ocupados) {
                System.out.println(BULLET + " ID: " + recurso.getId() + 
                                 " | Tipo: " + recurso.getTipo() + 
                                 " | Ubicacion: " + recurso.getUbicacionActual());
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
        System.out.print("\033[8;2H"); // Posicionar cursor
        System.out.println(BOLD + YELLOW + "EMERGENCIAS ACTIVAS (" + emergencias.size() + ")" + RESET);
        System.out.println(SEPARATOR);
        
        if (emergencias.isEmpty()) {
            System.out.println("No hay emergencias registradas en este momento.");
        } else {
            for (Emergencia emergencia : emergencias) {
                System.out.println(BOLD + BLUE + "\nEmergencia #" + emergencia.getId() + RESET);
                System.out.println(BULLET + " Tipo: " + emergencia.getTipo().getNombre());
                System.out.println(BULLET + " Ubicación: " + emergencia.getUbicacion());
                System.out.println(BULLET + " Nivel de Gravedad: " + emergencia.getNivelGravedad());
                System.out.println(BULLET + " Estado: " + emergencia.getEstado());
                System.out.println(BULLET + " Recursos Asignados: " + emergencia.getRecursosAsignados().size());
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
        System.out.print("\033[8;2H"); // Posicionar cursor
        System.out.print(YELLOW + "Ingrese el ID de la emergencia a atender: " + RESET);
        return scanner.nextLine();
    }
    // Mostrar estadisticas...

}
