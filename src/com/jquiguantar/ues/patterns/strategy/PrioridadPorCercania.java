package com.jquiguantar.ues.patterns.strategy;
import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.utils.Ubicacion;
import com.jquiguantar.ues.utils.MapaUrbano;
import java.util.List;
import java.util.Comparator;   
// import java.util.Collections;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class PrioridadPorCercania implements EstrategiaPriorizacion {
    //Definimos un punto de referencia, por ejemplo, la base de operaciones
    private Ubicacion puntoReferencia;

    public PrioridadPorCercania(Ubicacion puntoReferencia) {
        this.puntoReferencia = puntoReferencia;
    }

    @Override
    public List<Emergencia> priorizar(List<Emergencia> emergencias) {
        if(puntoReferencia == null) {
            //Si no hay puntos de referencia, no se puede priorizar
            System.err.println("Error: No se puede priorizar por cercania sin un punto de referencia.");
            return new ArrayList<>(emergencias);
        }
        //Creamos una lista para almacenar las emergencias ordenadas por distancia
        List<Emergencia> listaPriorizada = new ArrayList<>(emergencias);
        //Ordenamos las emergencias por distancia a la base de operaciones las mas cercanas primero
        listaPriorizada = listaPriorizada.stream()
            .sorted(Comparator.comparingInt(e -> MapaUrbano.CalcularDistanciaManhattan(e.getUbicacion(), puntoReferencia)))
            .collect(Collectors.toList());
        return listaPriorizada;
    }
}
