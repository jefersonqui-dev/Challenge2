package com.jquiguantar.ues.main;

import com.jquiguantar.ues.patterns.singleton.SistemaGestionEmergencia;
import com.jquiguantar.ues.ui.ConsoleUI;

import java.util.List;
import com.jquiguantar.ues.model.resources.Recursos;
import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.emergencies.TipoEmergencia;
import com.jquiguantar.ues.model.emergencies.NivelGravedad;

public class MainApp {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.limpiarConsola();
        System.out.println("===============================================");
        System.out.println("    SISTEMA DE GESTIÓN DE EMERGENCIAS URBANAS");
        System.out.println("===============================================");
        System.out.println("Iniciando sistema...\n");

        SistemaGestionEmergencia sistema = SistemaGestionEmergencia.getInstance();
        int opcion = 0;

        while (opcion != 5) {
            ui.mostrarMenuPrincipal();
            opcion = ui.leerOpcion();
            switch (opcion) {
                case 1: // Registrar Emergencia
                    TipoEmergencia tipo = ui.solicitarTipoEmergencia();
                    String ubicacion = ui.solicitarUbicacion();
                    NivelGravedad gravedad = ui.solicitarNivelGravedad();

                    Emergencia emergencia = new Emergencia(tipo, ubicacion, gravedad);
                    sistema.registrarEmergencia(emergencia);
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
                            ui.mostrarMensajeError("No se pudieron asignar recursos a la emergencia ID: " + idSeleccionado);
                        }
                    } else {
                        ui.mostrarMensajeError("No hay emergencias activas para atender");
                    }
                    break;

                case 4: // Mostrar Estadisticas del Dia
                    ui.limpiarConsola();
                    System.out.println("ESTADÍSTICAS DEL DÍA");
                    System.out.println("--------------------");
                    System.out.println("Esta funcionalidad estará disponible próximamente.");
                    System.out.println("\nPresione ENTER para continuar...");
                    ui.scanner.nextLine();
                    break;

                case 5: // Finalizar la jornada y salir
                    ui.limpiarConsola();
                    System.out.println("===============================================");
                    System.out.println("    SISTEMA DE GESTIÓN DE EMERGENCIAS URBANAS");
                    System.out.println("===============================================");
                    System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
                    break;

                default:
                    if (opcion != -1) {
                        ui.mostrarMensajeError("Opción no válida. Por favor, intente nuevamente.");
                    }
                    break;
            }
        }
        ui.cerrarScanner();
    }
}
