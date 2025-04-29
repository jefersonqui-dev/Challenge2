package com.jquiguantar.ues.main;

import com.jquiguantar.ues.patterns.singleton.SistemaGestionEmergencia;
import com.jquiguantar.ues.ui.ConsoleUI;
import java.util.List;
import com.jquiguantar.ues.model.resources.Recursos;
import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.emergencies.TipoEmergencia;
import com.jquiguantar.ues.model.emergencies.NivelGravedad;
import com.jquiguantar.ues.patterns.factory.EmergenciaFactory;
import com.jquiguantar.ues.utils.Ubicacion;

public class MainApp {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.limpiarConsola();
        System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "===============================================" + ConsoleUI.RESET);
        System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "    SISTEMA DE GESTIÓN DE EMERGENCIAS URBANAS" + ConsoleUI.RESET);
        System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "===============================================" + ConsoleUI.RESET);
        System.out.println("Iniciando sistema...\n");

        SistemaGestionEmergencia sistema = SistemaGestionEmergencia.getInstance();
        int opcion = 0;

        while (opcion != 5) {
            ui.mostrarMenuPrincipal();
            opcion = ui.leerOpcion();
            switch (opcion) {
                case 1: // Registrar Emergencia
                    TipoEmergencia tipo = ui.solicitarTipoEmergencia();
                    Ubicacion ubicacion = ui.solicitarUbicacion();
                    NivelGravedad gravedad = ui.solicitarNivelGravedad();

                    Emergencia nuevaEmergencia = EmergenciaFactory.crearEmergencia(tipo, ubicacion, gravedad);
                    sistema.registrarEmergencia(nuevaEmergencia);
                    ui.mostrarMensajeExito("Emergencia registrada exitosamente");
                    break;

                case 2: // Ver el Estado Actual de Recursos
                    List<Recursos> recursosDisponibles = sistema.getRecursosDisponibles();
                    List<Recursos> recursosOcupados = sistema.getRecursosOcupados();
                    ui.mostrarEstadoRecursos(recursosDisponibles, recursosOcupados);
                    break;

                case 3: // Atender Emergencia
                    List<Emergencia> emergenciasActivas = sistema.getEmergenciasActivas();
                    ui.mostrarEmergenciasActivas(emergenciasActivas);
                    
                    if (!emergenciasActivas.isEmpty()) {
                        String idSeleccionado = ui.solicitarIdEmergenciaAAtender();
                        boolean asignacionExitosa = sistema.asignarRecursosAEmergencia(idSeleccionado);
                        
                        if (asignacionExitosa) {
                            ui.mostrarMensajeExito("Recursos asignados exitosamente a la emergencia ID: " + idSeleccionado);
                        } else {
                            System.out.println("\nPresione ENTER para volver al menú principal...");
                            ui.scanner.nextLine();
                        }
                    } else {
                        ui.mostrarMensajeError("No hay emergencias activas para atender.");
                    }
                    break;

                case 4: // Mostrar Estadísticas del Día
                    System.out.println(ConsoleUI.YELLOW + "\n--- Estadísticas del Día ---" + ConsoleUI.RESET);
                    sistema.resolverEmergenciasEnProgreso();
                    sistema.generarEstadisticas();
                    break;

                case 5: // Finalizar la jornada y salir
                    System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "\nFinalizando la jornada..." + ConsoleUI.RESET);
                    sistema.resolverEmergenciasEnProgreso();
                    sistema.generarEstadisticas();
                    break;

                default:
                    if (opcion != -1) {
                        ui.mostrarMensajeError("Opción no válida. Intente nuevamente.");
                    }
                    break;
            }
        }
        ui.cerrarScanner();
        System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "\nSistema de Emergencias Urbanas Finalizado." + ConsoleUI.RESET);
    }
}
