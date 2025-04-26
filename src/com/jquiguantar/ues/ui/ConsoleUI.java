package com.jquiguantar.ues.ui;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner = new Scanner(System.in);

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
}
