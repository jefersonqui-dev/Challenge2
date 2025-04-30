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
                    ui.mostrarMensajeExito("Emergencia registrada exitosamente con ID: " + nuevaEmergencia.getId());
                    break;

                case 2: // Ver el Estado Actual de Recursos
                    List<Recursos> recursosDisponibles = sistema.getRecursosDisponibles();
                    List<Recursos> recursosOcupados = sistema.getRecursosOcupados();
                    ui.mostrarEstadoRecursos(recursosDisponibles, recursosOcupados);
                    break;

                case 3: // Atender Emergencia (Automático por Prioridad)
                    List<Emergencia> emergenciasActivas = sistema.getEmergenciasActivas();
                    ui.mostrarEmergenciasActivas(emergenciasActivas);
                    if (emergenciasActivas.stream().anyMatch(e -> e.getEstado() == com.jquiguantar.ues.model.emergencies.EstadoEmergencia.PENDIENTE)) {
                        System.out.println("\nPresione ENTER para intentar atender la emergencia más prioritaria (la primera de la lista)..." + ConsoleUI.RESET);
                        ui.scanner.nextLine();
                        boolean seIntentoAtender = sistema.atenderSiguienteEmergenciaPrioritaria();
                        if (!seIntentoAtender) {
                            ui.mostrarMensajeError("No se pudo iniciar la atención.");
                        } else {
                            System.out.println("\nPresione ENTER para volver al menú...");
                            ui.scanner.nextLine();
                        }
                    } else {
                        ui.mostrarMensajeError("No hay emergencias PENDIENTES para atender.");
                    }
                    break;

                case 4: // Mostrar Estadísticas del Día
                    ui.mostrarEstadisticas(
                        sistema.getTotalEmergenciasAtendidas(), 
                        sistema.getEmergenciasAtendidasPorTipo(), 
                        sistema.getTiempoTotalRespuestaPorTipoMilis()
                    );
                    break;

                case 5: // Finalizar Programa
                    System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "\nFinalizando la jornada..." + ConsoleUI.RESET);
                    System.out.println(ConsoleUI.YELLOW + "Resolviendo emergencias pendientes/en progreso (simulación)..." + ConsoleUI.RESET);
                    sistema.resolverEmergenciasEnProgreso();
                    System.out.println(ConsoleUI.YELLOW + "Generando estadísticas finales..." + ConsoleUI.RESET);
                    ui.mostrarEstadisticas(
                        sistema.getTotalEmergenciasAtendidas(), 
                        sistema.getEmergenciasAtendidasPorTipo(), 
                        sistema.getTiempoTotalRespuestaPorTipoMilis()
                    );
                    System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "Guardando registros (simulado)..." + ConsoleUI.RESET);
                    System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "Saliendo del sistema." + ConsoleUI.RESET);
                    break;

                default:
                    ui.mostrarMensajeError("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
        ui.cerrarScanner();
        System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "\nSistema de Emergencias Urbanas Finalizado." + ConsoleUI.RESET);
    }
}
