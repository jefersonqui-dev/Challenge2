package com.jquiguantar.ues.main;

import com.jquiguantar.ues.patterns.singleton.SistemaGestionEmergencia;
import com.jquiguantar.ues.ui.ConsoleUI;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema De Emergencias Urbanas...");

        SistemaGestionEmergencia sistema = SistemaGestionEmergencia.getInstance();

        ConsoleUI ui = new ConsoleUI();
        int opcion = 0;
        // Iniciamos el bucle pricipal de la aplicacion
        switch (opcion) {
            case 1:
                System.out.println("\n--- Registrar Nueva Emergencia ---");

                break;

            case 2:
                System.out.println("\n--- Estado Actual de Recursos ---");

                break;

            case 3:
                System.out.println("\n--- Atender Emergencia ---");

                break;

            case 4:
                System.out.println("\n--- Estadisticas del Dia ---");

                break;

            case 5:
                System.out.println("\n--- Finalizar la jornada y salir ---");

                break;

            default:
                if (opcion != 0) {
                    System.out.println("\nOpcion no valida. Intente nuevamente.");

                }
                break;

        }
        // Pequeña pausa para que el usuario pueda leer el mensaje
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ui.cerrarScanner();
        System.out.println("Sistema De Emergencias Urbanas Finalizado.");
    }
}
