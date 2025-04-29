package com.jquiguantar.ues.patterns.observer;
import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.patterns.singleton.SistemaGestionEmergencia;

public class ConsoleNotificationObserver implements ObservadorEmergencia {
    @Override
    public void actualizar(Emergencia emergencia) {
        System.out.println(SistemaGestionEmergencia.YELLOW + 
        "[Notificacion Consola] Nueva/Actualizada: ID= " + emergencia.getId() + 
        "Tipo " + emergencia.getTipo().getNombre() + 
        "Estado " + emergencia.getEstado() + 
         SistemaGestionEmergencia.RESET);
         
    }
}
