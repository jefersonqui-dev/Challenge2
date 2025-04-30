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

        while (opcion != 7) {
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

                case 3: // Atender Siguiente Emergencia (Automático)
                    List<Emergencia> emergenciasActivasAntes = sistema.getEmergenciasActivas();
                    ui.mostrarEmergenciasActivas(emergenciasActivasAntes);
                    System.out.println("\nPresione ENTER para intentar atender la más prioritaria...");
                    ui.scanner.nextLine();
                    
                    boolean seIntentoAtender = sistema.atenderSiguienteEmergenciaPrioritaria();
                    
                    if (!seIntentoAtender) {
                        ui.mostrarMensajeError("No se pudo iniciar la atención (ver detalles arriba).");
                    } else {
                        System.out.println("\nPresione ENTER para volver al menú principal...");
                        ui.scanner.nextLine();
                    }
                    break;

                case 4: // Resolver Emergencias en Progreso (Simulación)
                    System.out.println(ConsoleUI.YELLOW + "\nSimulando resolución de emergencias..." + ConsoleUI.RESET);
                    sistema.resolverEmergenciasEnProgreso();
                    ui.mostrarMensajeExito("Simulación de resolución completada.");
                    break;
                
                case 5: // Cambiar Estrategia de Priorización
                    ui.seleccionarEstrategiaPriorizacion();
                    break;

                case 6: // Mostrar Estadísticas del Día (ahora usa el método de UI)
                    ui.mostrarEstadisticas(
                        sistema.getTotalEmergenciasAtendidas(), 
                        sistema.getEmergenciasAtendidasPorTipo(), 
                        sistema.getTiempoTotalRespuestaPorTipoMilis()
                    );
                    break;

                case 7: // Finalizar la jornada y salir
                    System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "\nFinalizando la jornada. Generando Estadisticas Finales..." + ConsoleUI.RESET);
                    sistema.resolverEmergenciasEnProgreso();
                    ui.mostrarEstadisticas(
                        sistema.getTotalEmergenciasAtendidas(), 
                        sistema.getEmergenciasAtendidasPorTipo(), 
                        sistema.getTiempoTotalRespuestaPorTipoMilis()
                    );
                    System.out.println(ConsoleUI.BOLD + ConsoleUI.BLUE + "Saliendo del sistema..." + ConsoleUI.RESET);
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
