package com.jquiguantar.ues.view;

import java.util.Scanner;

import com.jquiguantar.ues.model.emergencies.EmergencyType;

public class ConsolaView {
    private Scanner scanner;

    public ConsolaView() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.err.println("\n=============================================================");
        System.out.println("            Sistema de Gestión de Emergencias Urbanas            ");
        System.out.println("=============================================================");
        System.out.println(
                "1. Registrar Nueva emergencia\n" +
                        "2. Ver Estado Actual de emergencia\n" +
                        "3. Ver Estado Actual de Recursos\n" +
                        "4. Gestionar Emergencias Activas\n" +
                        "5. Mostrar Estadisticas del dia\n" +
                        "6. Finalizar Jornada\n");
    }

    public EmergencyType requestEmergencyType() {
        showMessaje("\n==== Tipo de Emergencia ====");
    }

    public int requestOption() {
        System.out.print("Elija Una Opción: ");
        while (!scanner.hasNextInt()) {
            displayInputErrorMessaje("Numero");
            scanner.next();
        }
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public void displayInputErrorMessaje(String tipo) {
        showMessaje("Entrada No Válida. Por favor, ingrese un " + tipo + ".");
    }

    public void showMessaje(String messaje) {
        System.out.println(messaje);
    }
}
