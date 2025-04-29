// package com.jquiguantar.ues.ui;

// import com.jquiguantar.ues.model.emergencies.Emergencia;
// import com.jquiguantar.ues.model.resources.Recursos;
// import java.util.List;

// public class ConsolaManager {
//     // Constantes de colores
//     public static final String RESET = "\u001B[0m";
//     public static final String RED = "\u001B[31m";
//     public static final String YELLOW = "\u001B[33m";
//     public static final String GREEN = "\u001B[32m";
//     public static final String BLUE = "\u001B[34m";
//     public static final String BOLD = "\u001B[1m";

//     // Constantes para el diseño
//     private static final int ANCHO_PANEL = 40;
//     private static final int ANCHO_TOTAL = 100;
//     private static final String LINEA_HORIZONTAL = "═";
//     private static final String LINEA_VERTICAL = "║";
//     private static final String ESQUINA_SUP_IZQ = "╔";
//     private static final String ESQUINA_SUP_DER = "╗";
//     private static final String ESQUINA_INF_IZQ = "╚";
//     private static final String ESQUINA_INF_DER = "╝";

//     private List<Emergencia> emergenciasActivas;
//     private List<Recursos> recursosDisponibles;
//     private List<Recursos> recursosOcupados;

//     public void actualizarEstado(List<Emergencia> emergencias, List<Recursos> disponibles, List<Recursos> ocupados) {
//         this.emergenciasActivas = emergencias;
//         this.recursosDisponibles = disponibles;
//         this.recursosOcupados = ocupados;
//     }

//     public void limpiarConsola() {
//         System.out.print("\033[H\033[2J");
//         System.out.flush();
//     }

//     public void dibujarMarco() {
//         // Dibujar línea superior
//         System.out.print(ESQUINA_SUP_IZQ);
//         for (int i = 0; i < ANCHO_TOTAL - 2; i++) {
//             System.out.print(LINEA_HORIZONTAL);
//         }
//         System.out.println(ESQUINA_SUP_DER);

//         // Dibujar líneas verticales
//         for (int i = 0; i < 20; i++) {
//             System.out.print(LINEA_VERTICAL);
//             for (int j = 0; j < ANCHO_TOTAL - 2; j++) {
//                 System.out.print(" ");
//             }
//             System.out.println(LINEA_VERTICAL);
//         }

//         // Dibujar línea inferior
//         System.out.print(ESQUINA_INF_IZQ);
//         for (int i = 0; i < ANCHO_TOTAL - 2; i++) {
//             System.out.print(LINEA_HORIZONTAL);
//         }
//         System.out.println(ESQUINA_INF_DER);
//     }

//     public void mostrarPanelLateral() {
//         // Posicionar el cursor para el panel lateral
//         System.out.print("\033[2;60H"); // Mover a la columna 60, fila 2
        
//         // Título del panel
//         System.out.println(BOLD + YELLOW + "═ PANEL DE ESTADO ═" + RESET);
        
//         // Emergencias activas
//         System.out.println(BOLD + BLUE + "\nEmergencias Activas: " + RESET + emergenciasActivas.size());
//         for (Emergencia e : emergenciasActivas) {
//             System.out.println("• " + e.getTipo().getNombre() + " (" + e.getNivelGravedad() + ")");
//         }

//         // Recursos disponibles
//         System.out.println(BOLD + GREEN + "\nRecursos Disponibles: " + RESET + recursosDisponibles.size());
//         for (Recursos r : recursosDisponibles) {
//             System.out.println("• " + r.getTipo() + " (" + r.getId() + ")");
//         }

//         // Recursos ocupados
//         System.out.println(BOLD + RED + "\nRecursos Ocupados: " + RESET + recursosOcupados.size());
//         for (Recursos r : recursosOcupados) {
//             System.out.println("• " + r.getTipo() + " (" + r.getId() + ")");
//         }
//     }

//     public void mostrarMenuPrincipal() {
//         // Posicionar el cursor para el menú principal
//         System.out.print("\033[2;2H"); // Mover a la columna 2, fila 2
        
//         System.out.println(BOLD + BLUE + "SISTEMA DE GESTION DE EMERGENCIAS URBANAS" + RESET);
//         System.out.println("\nSeleccione una opción:");
//         System.out.println("1. Registrar Emergencia");
//         System.out.println("2. Ver Estado de Recursos");
//         System.out.println("3. Atender Emergencia");
//         System.out.println("4. Mostrar Estadísticas");
//         System.out.println("5. Salir");
//         System.out.print("\nOpción: ");
//     }

//     public void actualizarConsola() {
//         limpiarConsola();
//         dibujarMarco();
//         mostrarPanelLateral();
//         mostrarMenuPrincipal();
//     }
// } 