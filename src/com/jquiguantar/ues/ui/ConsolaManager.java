package com.jquiguantar.ues.ui;

import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.resources.Recursos;
import java.util.List;

public class ConsolaManager {
    // Constantes de colores
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String BOLD = "\u001B[1m";

    // Constantes para el diseño
    private static final int ANCHO_PANEL = 30;
    private static final int ANCHO_TOTAL = 100;
    private static final int ALTO_TOTAL = 15;
    private static final String LINEA_HORIZONTAL = "═";
    private static final String LINEA_VERTICAL = "║";
    private static final String ESQUINA_SUP_IZQ = "╔";
    private static final String ESQUINA_SUP_DER = "╗";
    private static final String ESQUINA_INF_IZQ = "╚";
    private static final String ESQUINA_INF_DER = "╝";
    private static final String SEPARADOR_VERTICAL = "║";
    private static final String SEPARADOR_HORIZONTAL = "═";
    private static final String BULLET = "-";

    private List<Emergencia> emergenciasActivas;
    private List<Recursos> recursosDisponibles;
    private List<Recursos> recursosOcupados;

    public void actualizarEstado(List<Emergencia> emergencias, List<Recursos> disponibles, List<Recursos> ocupados) {
        this.emergenciasActivas = emergencias;
        this.recursosDisponibles = disponibles;
        this.recursosOcupados = ocupados;
    }

    public void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void dibujarMarco() {
        // Dibujar línea superior
        System.out.print(ESQUINA_SUP_IZQ);
        for (int i = 0; i < ANCHO_TOTAL - ANCHO_PANEL - 3; i++) {
            System.out.print(LINEA_HORIZONTAL);
        }
        System.out.print(ESQUINA_SUP_DER);
        for (int i = 0; i < ANCHO_PANEL; i++) {
            System.out.print(LINEA_HORIZONTAL);
        }
        System.out.println(ESQUINA_SUP_DER);

        // Dibujar encabezados
        System.out.print(LINEA_VERTICAL);
        
        // Panel izquierdo
        String tituloPrincipal = "SISTEMA DE GESTION DE EMERGENCIAS";
        int anchoPanelPrincipal = ANCHO_TOTAL - ANCHO_PANEL - 3;
        int espaciosIzquierda = (anchoPanelPrincipal - tituloPrincipal.length()) / 2;
        
        // Imprimir espacios antes del título
        for (int i = 0; i < espaciosIzquierda; i++) {
            System.out.print(" ");
        }
        
        // Imprimir título principal
        System.out.print(BOLD + BLUE + tituloPrincipal + RESET);
        
        // Imprimir espacios después del título
        int espaciosRestantes = anchoPanelPrincipal - espaciosIzquierda - tituloPrincipal.length();
        for (int i = 0; i < espaciosRestantes; i++) {
            System.out.print(" ");
        }
        
        // Separador vertical
        System.out.print(LINEA_VERTICAL);
        
        // Panel derecho
        String tituloPanel = "PANEL DE ESTADO";
        int espaciosDerecha = (ANCHO_PANEL - tituloPanel.length()) / 2;
        
        // Imprimir espacios antes del título del panel
        for (int i = 0; i < espaciosDerecha; i++) {
            System.out.print(" ");
        }
        
        // Imprimir título del panel
        System.out.print(BOLD + YELLOW + tituloPanel + RESET);
        
        // Imprimir espacios después del título del panel
        int espaciosRestantesDerecha = ANCHO_PANEL - espaciosDerecha - tituloPanel.length();
        for (int i = 0; i < espaciosRestantesDerecha; i++) {
            System.out.print(" ");
        }
        
        System.out.println(LINEA_VERTICAL);

        // Dibujar línea divisoria
        System.out.print(LINEA_VERTICAL);
        for (int i = 0; i < ANCHO_TOTAL - ANCHO_PANEL - 3; i++) {
            System.out.print(SEPARADOR_HORIZONTAL);
        }
        System.out.print(LINEA_VERTICAL);
        for (int i = 0; i < ANCHO_PANEL; i++) {
            System.out.print(SEPARADOR_HORIZONTAL);
        }
        System.out.println(LINEA_VERTICAL);

        // Dibujar líneas verticales
        for (int i = 0; i < ALTO_TOTAL - 3; i++) {
            System.out.print(LINEA_VERTICAL);
            for (int j = 0; j < ANCHO_TOTAL - ANCHO_PANEL - 3; j++) {
                System.out.print(" ");
            }
            System.out.print(LINEA_VERTICAL);
            for (int j = 0; j < ANCHO_PANEL; j++) {
                System.out.print(" ");
            }
            System.out.println(LINEA_VERTICAL);
        }

        // Dibujar línea inferior
        System.out.print(ESQUINA_INF_IZQ);
        for (int i = 0; i < ANCHO_TOTAL - ANCHO_PANEL - 3; i++) {
            System.out.print(LINEA_HORIZONTAL);
        }
        System.out.print(ESQUINA_INF_DER);
        for (int i = 0; i < ANCHO_PANEL; i++) {
            System.out.print(LINEA_HORIZONTAL);
        }
        System.out.println(ESQUINA_INF_DER);
    }

    public void mostrarPanelLateral() {
        // Posicionar el cursor para el panel lateral
        int posicionPanelX = ANCHO_TOTAL - ANCHO_PANEL + 2;
        
        // Emergencias activas
        System.out.print("\033[4;" + posicionPanelX + "H");
        System.out.println(BOLD + BLUE + "Emergencias Activas: " + RESET + emergenciasActivas.size());
        int linea = 5;
        for (Emergencia e : emergenciasActivas) {
            System.out.print("\033[" + linea + ";" + posicionPanelX + "H");
            System.out.println(BULLET + " " + e.getTipo().getNombre() + 
                             " (" + e.getNivelGravedad() + ")");
            linea++;
        }

        // Recursos disponibles
        linea += 1;
        System.out.print("\033[" + linea + ";" + posicionPanelX + "H");
        System.out.println(BOLD + GREEN + "Recursos Disponibles: " + RESET + recursosDisponibles.size());
        linea++;
        for (Recursos r : recursosDisponibles) {
            System.out.print("\033[" + linea + ";" + posicionPanelX + "H");
            System.out.println(BULLET + " " + r.getTipo() + " (" + r.getId() + ")");
            linea++;
        }

        // Recursos ocupados
        linea += 1;
        System.out.print("\033[" + linea + ";" + posicionPanelX + "H");
        System.out.println(BOLD + RED + "Recursos Ocupados: " + RESET + recursosOcupados.size());
        linea++;
        for (Recursos r : recursosOcupados) {
            System.out.print("\033[" + linea + ";" + posicionPanelX + "H");
            System.out.println(BULLET + " " + r.getTipo() + " (" + r.getId() + ")");
            linea++;
        }
    }

    public void mostrarMenuPrincipal() {
        // Solo dibujar el marco y el panel lateral
        dibujarMarco();
        mostrarPanelLateral();
    }

    public void limpiarAreaEntrada() {
        // Limpiar el área donde se muestra la opción seleccionada
        System.out.print("\033[13;3H"); // Posicionar en la línea de entrada
        System.out.print("\033[K");     // Limpiar la línea actual
        System.out.print("\033[14;3H"); // Posicionar en la siguiente línea
        System.out.print("\033[K");     // Limpiar la siguiente línea
        System.out.print("\033[15;3H"); // Posicionar en la siguiente línea
        System.out.print("\033[K");     // Limpiar la siguiente línea
    }

    public void actualizarConsola() {
        limpiarConsola();
        mostrarMenuPrincipal();
        limpiarAreaEntrada();
    }
} 