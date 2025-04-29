
package com.jquiguantar.ues.patterns.factory;
//esta clase no necesita mantener estado(no es un Singleton no necesita atributos de instancia)

//su metodo sera estatico

import com.jquiguantar.ues.model.emergencies.Emergencia;
import com.jquiguantar.ues.model.emergencies.TipoEmergencia;
import com.jquiguantar.ues.model.emergencies.NivelGravedad;
import com.jquiguantar.ues.utils.Ubicacion;

/**
 * Fabrica para crear instancias de Emergencia
 * centraliza la logica de creacion de objetos de Emergencia
 * y permite la extensibilidad en el futuro si se agregan nuevos tipos de
 * Emergencia
 */
public class EmergenciaFactory {

    /// Constructor privado para evitar instanciacion
    private EmergenciaFactory() {

    }

    /**
     * Crea una nueva instancia de Emergencia
     * 
     * @param tipo      Tipo de Emergencia a crear
     * @param ubicacion Ubicacion de la Emergencia
     * @param gravedad  Nivel de Gravedad de la Emergencia
     * @return Nueva instancia de Emergencia creada
     */
    public static Emergencia crearEmergencia(TipoEmergencia tipo, Ubicacion ubicacion, NivelGravedad gravedad) {
        // La logica de creacion se centraliza aqui
        // Por ahora es solo llamar al constructor privado de Emergencia

        return new Emergencia(tipo, ubicacion, gravedad);
    }

}
