package com.jquiguantar.ues.main;

import com.jquiguantar.ues.patterns.singleton.SistemaGestionEmergencia;
import com.jquiguantar.ues.ui.ConsoleUI;
import com.jquiguantar.ues.ui.ConsolaManager;

import java.util.List;
import com.jquiguantar.ues.model.resources.Recursos;
import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.emergencies.TipoEmergencia;
import com.jquiguantar.ues.model.emergencies.NivelGravedad;

public class MainApp {
    // Constantes de colores que funcionan en la consola
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";      // Color piel
    public static final String YELLOW = "\u001B[33m";   // Color violeta
    public static final String GREEN = "\u001B[32m";    // Color verde claro
    public static final String BLUE = "\u001B[34m";     // Color amarillo
    public static final String BOLD = "\u001B[1m";      // Negrita

    public static void main(String[] args) {
        SistemaGestionEmergencia sistema = SistemaGestionEmergencia.getInstance();
        ConsoleUI ui = new ConsoleUI();
        ConsolaManager consola = new ConsolaManager();

        int opcion = 0;
        while (opcion != 5) {
            // Actualizar el estado de la consola
            consola.actualizarEstado(
                sistema.getEmergenciasActivas(),
                sistema.getRecursosDisponibles(),
                sistema.getRecursosOcupados()
            );
            
            // Limpiar y mostrar el marco y panel lateral solo al inicio
            if (opcion == 0) {
                consola.actualizarConsolaCompleta();
            } else {
                consola.actualizarConsola();
            }
            
            // Mostrar el menú principal
            ui.mostrarMenuPrincipal();
            
            // Leer la opción del usuario
            opcion = ui.leerOpcion();
            
            switch (opcion) {
                case 1: // Registrar Emergencia
                    consola.actualizarConsola(); // Limpiar solo el área de contenido
                    TipoEmergencia tipo = ui.solicitarTipoEmergencia();
                    String ubicacion = ui.solicitarUbicacion();
                    NivelGravedad gravedad = ui.solicitarNivelGravedad();

                    Emergencia emergencia = new Emergencia(tipo, ubicacion, gravedad);
                    sistema.registrarEmergencia(emergencia);
                    System.out.println(GREEN + "Emergencia registrada exitosamente" + RESET);
                    ui.esperarEntradaUsuario();
                    break;

                case 2: // Ver el Estado Actual de Recursos
                    consola.actualizarConsola(); // Limpiar solo el área de contenido
                    List<Recursos> recursosDisponibles = sistema.getRecursosDisponibles();
                    List<Recursos> recursosOcupados = sistema.getRecursosOcupados();
                    ui.mostrarEstadoRecursos(recursosDisponibles, recursosOcupados);
                    ui.esperarEntradaUsuario();
                    break;

                case 3: // Atender Emergencia
                    consola.actualizarConsola(); // Limpiar solo el área de contenido
                    List<Emergencia> emergenciasActivas = sistema.getEmergenciasActivas();
                    ui.mostrarEmergenciasActivas(emergenciasActivas);
                    String idEmergencia = ui.solicitarIdEmergenciaAAtender();
                    boolean asignacionExitosa = sistema.asignarRecursosAEmergencia(idEmergencia);
                    if (asignacionExitosa) {
                        System.out.println(GREEN + "Recursos asignados exitosamente a la emergencia con ID: " + idEmergencia + RESET);
                    } else {
                        System.out.println(RED + "No se pudieron asignar recursos a la emergencia con ID: " + idEmergencia + RESET);
                    }
                    ui.esperarEntradaUsuario();
                    break;

                case 4: // Mostrar Estadísticas del Dia
                    consola.actualizarConsola(); // Limpiar solo el área de contenido
                    System.out.println(YELLOW + "--- Estadísticas del Día ---" + RESET);
                    // Aquí irían las estadísticas reales
                    ui.esperarEntradaUsuario();
                    break;

                case 5: // Finalizar la jornada y salir
                    System.out.println(BLUE + "\n--- Finalizar la jornada y salir ---" + RESET);
                    break;

                default:
                    if (opcion != -1) {
                        System.out.println(RED + "Opción no válida. Intente nuevamente." + RESET);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        ui.cerrarScanner();
        System.out.println(BOLD + BLUE + "Sistema de Emergencias Urbanas Finalizado." + RESET);
    }
}
