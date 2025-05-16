package com.jquiguantar.ues.controller;

import com.jquiguantar.ues.view.ConsolaView;

public class MainApp {
    private static ConsolaView view;

    public static void main(String[] args) {
        view = new ConsolaView();
        int mainOption;
        do {
            view.showMenu();
            mainOption = view.requestOption();
            switch (mainOption) {
                case 1:
                    registerNewEmergency();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;

                default:
                    break;
            }
        } while (mainOption != 6);
    }

    private static void registerNewEmergency() {
        view.showMessaje("\n===Registrar Nueva Emergencia===");
    }
}
