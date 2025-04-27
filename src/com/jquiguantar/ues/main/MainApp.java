package com.jquiguantar.ues.main;

import com.jquiguantar.ues.patterns.singleton.SistemaGestionEmergencia;
import com.jquiguantar.ues.ui.ConsoleUI;

import java.util.List;
import com.jquiguantar.ues.model.resources.Recursos;
import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.emergencies.TipoEmergencia;
import com.jquiguantar.ues.model.emergencies.NivelGravedad;

import com.jquiguantar.ues.patterns.factory.EmergenciaFactory;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema De Emergencias Urbanas...");

        SistemaGestionEmergencia sistema = SistemaGestionEmergencia.getInstance();

        ConsoleUI ui = new ConsoleUI();
        int opcion = 0;
        // Iniciamos el bucle pricipal de la aplicacion
        while (opcion != 5) {
            ui.mostrarMenuPrincipal();
            opcion = ui.leerOpcion();
            switch (opcion) {
                case 1: // Registrar Emergencia
                    System.out.println("\n --- Registrar Nueva Emergencia ---");
                    // 1. Solicitar detalles usando la UI
                    TipoEmergencia tipo = ui.solicitarTipoEmergencia();
                    String ubicacion = ui.solicitarUbicacion();
                    NivelGravedad gravedad = ui.solicitarNivelGravedad();

                    // 2. Usamos el patron Factory para crear la INSTANCIA de Emergencia
                    Emergencia nuevaEmergencia = EmergenciaFactory.crearEmergencia(tipo, ubicacion, gravedad);

                    // 3. Registrar la emergencia en el sistema
                    sistema.registrarEmergencia(nuevaEmergencia);
                    System.out.println("Emergencia Registrada con exito...");
                    break;
                case 2: // Ver el Estado Actual de Recursos

                    System.out.println("\n --- Estado Actual de Recursos ---");
                    // 1. Obtener las listas de recursos Singleton
                    List<Recursos> recursosDisponibles = sistema.getRecursosDisponibles();
                    List<Recursos> recursosOcupados = sistema.getRecursosOcupados();
                    ui.mostrarEstadoRecursos(recursosDisponibles, recursosOcupados);

                    // 2. Pasar las listas a la UI para que las muestres
                    ui.mostrarEstadoRecursos(recursosDisponibles, recursosOcupados);

                    break;
                case 3: // Atender Emergencia
                    System.out.println("\n --- Atender Emergencia ---");
                    break;
                case 4: // Mostrar Estadisticas del Dia
                    System.out.println("\n --- Estadisticas del Dia ---");
                    break;
                case 5: // Finalizar la jornada y salir
                    System.out.println("\n --- Finalizar la jornada y salir ---");
                    break;
                default:
                    if (opcion != -1) {
                        System.out.println("Opcion no valida. Intente nuevamente.");
                    }
                    break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ui.cerrarScanner();
        System.out.println("Sistema de Emergencias Urbanas Finalizado.");
    }
}
