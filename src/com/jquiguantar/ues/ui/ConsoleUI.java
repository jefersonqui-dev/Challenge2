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
    public Scanner scanner;

    private List<TipoEmergencia> TiposEmergenciaDisponibles;

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
        System.out.println("===============================================");
        System.out.println("    SISTEMA DE GESTIÓN DE EMERGENCIAS URBANAS");
        System.out.println("===============================================");
        System.out.println("1. Registrar Emergencia");
        System.out.println("2. Ver Estado de Recursos");
        System.out.println("3. Atender Emergencia");
        System.out.println("4. Estadísticas del Día");
        System.out.println("5. Salir");
        System.out.println("===============================================");
    }

    /**
     * Lee la opcion ingresada por el usuario desde la consola.
     * Implementa validacion basica para asegurar que es un numero
     * 
     * @return La opcion ingresada por el usuario
     */

    public int leerOpcion() {
        int opcion = -1;
        System.out.print("Por favor, ingrese el número de la opción deseada: ");
        try {
            String input = scanner.nextLine();
            opcion = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida. Intente nuevamente.");
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
        System.out.println("REGISTRAR NUEVA EMERGENCIA");
        System.out.println("--------------------------");
        System.out.println("Seleccione el tipo de emergencia:");
        
        for (int i = 0; i < TiposEmergenciaDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + TiposEmergenciaDisponibles.get(i).getNombre());
        }

        TipoEmergencia tipoSeleccionado = null;
        while (tipoSeleccionado == null) {
            System.out.print("\nIngrese el número del tipo: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion > 0 && opcion <= TiposEmergenciaDisponibles.size()) {
                    tipoSeleccionado = TiposEmergenciaDisponibles.get(opcion - 1);
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Intente nuevamente.");
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
        System.out.print("\nIngrese la ubicación de la emergencia: ");
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
        System.out.println("\nNIVEL DE GRAVEDAD");
        System.out.println("-----------------");
        
        NivelGravedad[] gravedades = NivelGravedad.values();
        for (int i = 0; i < gravedades.length; i++) {
            System.out.println((i + 1) + ". " + gravedades[i]);
        }

        NivelGravedad gravedadSeleccionada = null;
        while (gravedadSeleccionada == null) {
            System.out.print("\nIngrese el número del nivel de gravedad: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion > 0 && opcion <= gravedades.length) {
                    gravedadSeleccionada = gravedades[opcion - 1];
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        return gravedadSeleccionada;
    }

    public void mostrarEstadoRecursos(List<Recursos> disponibles, List<Recursos> ocupados) {
        limpiarConsola();
        System.out.println("ESTADO DE RECURSOS");
        System.out.println("------------------");
        
        System.out.println("\nRecursos Disponibles (" + disponibles.size() + "):");
        if (disponibles.isEmpty()) {
            System.out.println("No hay recursos disponibles en este momento.");
        } else {
            for (Recursos recurso : disponibles) {
                System.out.println("- ID: " + recurso.getId() + 
                                 " | Tipo: " + recurso.getTipo() + 
                                 " | Ubicación: " + recurso.getUbicacionActual());
            }
        }
        
        System.out.println("\nRecursos Ocupados (" + ocupados.size() + "):");
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
        System.out.println("EMERGENCIAS ACTIVAS");
        System.out.println("-------------------");
        
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
        System.out.println("Ingrese el ID de la emergencia a atender:");
        return scanner.nextLine();
    }

    public void mostrarMensajeExito(String mensaje) {
        System.out.println("\n✓ " + mensaje);
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    public void mostrarMensajeError(String mensaje) {
        System.out.println("\n✗ " + mensaje);
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    // Mostrar estadisticas...

}
