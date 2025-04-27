package com.jquiguantar.ues.ui;

import com.jquiguantar.ues.model.emergencies.NivelGravedad;
import com.jquiguantar.ues.model.emergencies.TipoEmergencia;
//Importamos las clases concretas de tipos de emergencia que hemos creado(Incendio,accidenteVehicular,Robo, etc)
import com.jquiguantar.ues.model.emergencies.AccidenteVehicular;
import com.jquiguantar.ues.model.emergencies.Incendio;
import com.jquiguantar.ues.model.emergencies.Robo;
import com.jquiguantar.ues.model.resources.Recursos;
//import com.jquiguantar.ues.model.resources.TipoRecurso;

//import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

// Clase para manejar la interaccion con el usuario a traves de consola

public class ConsoleUI {
    private Scanner scanner;

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

    /**
     * Muestra el Menu principal de Opciones de Usuario
     */
    public void mostrarMenuPrincipal() {
        System.out.println("\n===============================================================");
        System.out.println("            SISTEMA DE GESTION DE EMERGENCIAS URBANAS");
        System.out.println("=================================================================");
        System.out.println("Por favor, seleccione una de las siguientes opciones:");
        System.out.println("1. Registrar Emergencia");
        System.out.println("2. Ver el Estado Actual de Recursos");
        System.out.println("3. Atender Emergencia");
        System.out.println("4. Mostrar Estadisticas del Dia");
        System.out.println("5. Finalizar la jornada y salir");
        System.out.println("===============================================================\n");
    }

    /**
     * Lee la opcion ingresada por el usuario desde la consola.
     * Implementa validacion basica para asegurar que es un numero
     * 
     * @return La opcion ingresada por el usuario
     */

    public int leerOpcion() {
        int opcion = -1;
        System.out.println("Por favor, ingrese el numero de la opcion deseada: ");
        try {
            opcion = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Opcion no valida. Intente nuevamente.");
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
        System.out.println("\n--- Registrar Nueva Emergencia ---");
        System.out.println("Seleccione el tipo de emergencia: ");
        for (int i = 0; i < TiposEmergenciaDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + TiposEmergenciaDisponibles.get(i).getNombre());
        }

        TipoEmergencia tipoSeleccionado = null;
        while (tipoSeleccionado == null) {
            System.out.println("Ingrese el numero del tipo: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion > 0 && opcion <= TiposEmergenciaDisponibles.size()) {
                    tipoSeleccionado = TiposEmergenciaDisponibles.get(opcion - 1);
                } else {
                    System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opcion no valida. Intente nuevamente.");
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
        System.out.println("Ingrese la ubicacion de la emergencia: ");
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
        System.out.println("Ingrese el nivel de gravedad de la emergencia: ");
        NivelGravedad[] gravedades = NivelGravedad.values();
        for (int i = 0; i < gravedades.length; i++) {
            System.out.println((i + 1) + ". " + gravedades[i]);
        }

        NivelGravedad gravedadSeleccionada = null;
        while (gravedadSeleccionada == null) {
            System.out.println("Ingrese el numero del nivel de gravedad: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion > 0 && opcion <= gravedades.length) {
                    gravedadSeleccionada = gravedades[opcion - 1];
                } else {
                    System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opcion no valida. Intente nuevamente.");
            }

        }
        return gravedadSeleccionada;
    }

    public void mostrarEstadoRecursos(List<Recursos> disponibles, List<Recursos> ocupados) {
        System.out.println("\n--- ESTADO ACTUAL DE RECURSOS ---");

        System.out.println("\n--- Recursos Disponibles (" + disponibles.size() + ") ---");
        if (disponibles.isEmpty()) {
            System.out.println("No hay recursos disponibles en este momento.");
        } else {
            for (Recursos recurso : disponibles) {
                // Mustra la informacion basica de cada Recurso
                System.out.println("ID: " + recurso.getId() + ", Tipo: " + recurso.getTipo() + ", Ubicacion: "
                        + recurso.getUbicacionActual());
            }
        }
        System.out.println("\n--- Recursos Ocupados (" + ocupados.size() + ") ---");
        if (ocupados.isEmpty()) {
            System.out.println("No hay recursos ocupados en este momento.");
        } else {
            for (Recursos recurso : ocupados) {
                // Mustra la informacion basica de cada Recurso
                System.out.println("ID: " + recurso.getId() + ", Tipo: " + recurso.getTipo() + ", Ubicacion: "
                        + recurso.getUbicacionActual());
            }
        }
        System.out.println("===============================================================\n");
    }

}
