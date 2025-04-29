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
// Clase para manejar la interaccion con el usuario a triaves de consola

public class ConsoleUI {
    public Scanner scanner;

    private List<TipoEmergencia> TiposEmergenciaDisponibles;

    // Constantes de colores que funcionan en la consola
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";      // Color piel
    public static final String YELLOW = "\u001B[33m";   // Color violeta
    public static final String GREEN = "\u001B[32m";    // Color verde claro
    public static final String BLUE = "\u001B[34m";     // Color amarillo
    public static final String BOLD = "\u001B[1m";      // Negrita

    // CONSTRUCTOR

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);

        // Inicializa la lista de tipos de emergencia disponibles
        this.TiposEmergenciaDisponibles = new ArrayList<>();
        this.TiposEmergenciaDisponibles.add(new Incendio());
        this.TiposEmergenciaDisponibles.add(new AccidenteVehicular());
        this.TiposEmergenciaDisponibles.add(new Robo());
    }

    public void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Si falla la limpieza, simplemente imprimimos líneas en blanco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Muestra el Menu principal de Opciones de Usuario
     */
    public void mostrarMenuPrincipal() {
        limpiarConsola();
        System.out.println(BOLD + BLUE + "===============================================" + RESET);
        System.out.println(BOLD + BLUE + "    SISTEMA DE GESTIÓN DE EMERGENCIAS URBANAS" + RESET);
        System.out.println(BOLD + BLUE + "===============================================" + RESET);
        System.out.println("1. Registrar Emergencia");
        System.out.println("2. Ver Estado de Recursos");
        System.out.println("3. Atender Emergencia");
        System.out.println("4. Estadísticas del Día");
        System.out.println("5. Salir");
        System.out.println(BOLD + BLUE + "===============================================" + RESET);
    }

    /**
     * Lee la opcion ingresada por el usuario desde la consola.
     * Implementa validacion basica para asegurar que es un numero
     * 
     * @return La opcion ingresada por el usuario
     */

    public int leerOpcion() {
        int opcion = -1;
        System.out.print(YELLOW + "Por favor, ingrese el número de la opción deseada: " + RESET);
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
        limpiarConsola();
        System.out.println(BOLD + YELLOW + "REGISTRAR NUEVA EMERGENCIA" + RESET);
        System.out.println(BOLD + YELLOW + "--------------------------" + RESET);
        System.out.println("Seleccione el tipo de emergencia:");
        
        for (int i = 0; i < TiposEmergenciaDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + TiposEmergenciaDisponibles.get(i).getNombre());
        }

        TipoEmergencia tipoSeleccionado = null;
        while (tipoSeleccionado == null) {
            System.out.print(YELLOW + "\nIngrese el número del tipo: " + RESET);
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
    private int leerCoordenada(String mensaje) {
        int coordenada = Integer.MIN_VALUE;
        while (coordenada == Integer.MIN_VALUE) {
            System.out.print(mensaje);
            try {   
                coordenada = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Entrada no válida. Por favor, ingrese un número entero." + RESET);
            }
        }
        return coordenada;
    }

    /**
     * Solicita al usuario la ubucacion de la emergencia (coordenadas)
     * @return Un objeto Ubicacion con las coordenadas ingresadas
     */
    public Ubicacion solicitarUbicacion() {
        System.out.println("Ingrese la ubicacion de la emergencia (coordenadas X  Y): ");
        //llama a los metodos auxiliares para solicitar las coordenadas
        int x = leerCoordenada("X");
        int y = leerCoordenada("Y");
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

        NivelGravedad gravedadSeleccionada = null;
        while (gravedadSeleccionada == null) {
            System.out.print(YELLOW + "\nIngrese el número del nivel de gravedad: " + RESET);
            try {
                String input = scanner.nextLine();
                int opcion = Integer.parseInt(input);
                if (opcion > 0 && opcion <= gravedades.length) {
                    gravedadSeleccionada = gravedades[opcion - 1];
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
     * @param emergencias La lista de emergencias activas a mostrar.
     */

    public void mostrarEmergenciasActivas(List<Emergencia> emergencias) {
        limpiarConsola();
        System.out.println(BOLD + YELLOW + "EMERGENCIAS ACTIVAS" + RESET);
        System.out.println(BOLD + YELLOW + "-------------------" + RESET);
        
        if (emergencias.isEmpty()) {
            System.out.println("No hay emergencias activas en este momento.");
        } else {
            for (Emergencia emergencia : emergencias) {
                System.out.println("\nID: " + emergencia.getId());
                System.out.println("Tipo: " + emergencia.getTipo().getNombre());
                System.out.println("Ubicación: " + emergencia.getUbicacion());
                System.out.println("Gravedad: " + emergencia.getNivelGravedad());
                System.out.println("Estado: " + emergencia.getEstado());
                System.out.println("Recursos Asignados: " + emergencia.getRecursosAsignados().size());
                System.out.println("-------------------");
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
        System.out.print(YELLOW + "Ingrese el ID de la emergencia a atender: " + RESET);
        return scanner.nextLine();
    }

    public void mostrarMensajeExito(String mensaje) {
        System.out.println(GREEN + "\n✓ " + mensaje + RESET);
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    public void mostrarMensajeError(String mensaje) {
        System.out.println(RED + "\n✗ " + mensaje + RESET);
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    // Mostrar estadisticas...

}
