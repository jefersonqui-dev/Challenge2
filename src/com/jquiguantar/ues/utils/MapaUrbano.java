package com.jquiguantar.ues.utils;
//import com.jquiguantar.ues.utils.Ubicacion;
public class MapaUrbano {
    //Constructor Privado
    private MapaUrbano(){
        //No permitir crear instancias de esta clase
    }

    /**
     * Calcula la distancia Manhattan entre dos ubicaciones.
     * @param u1 La primera ubicación.
     * @param u2 La segunda ubicación.
     * @return La distancia Manhattan como un entero.
     */
    public static int CalcularDistanciaManhattan(Ubicacion u1, Ubicacion u2){
        if(u1 == null || u2 == null){
            return -1; // devolver -1 para un error
        }
        
        // Calcula el valor absoluto de la diferencia en X y en Y y los suma
        return Math.abs(u1.getX() - u2.getX()) + Math.abs(u1.getY() - u2.getY());
    }
        // // Calculo de distancia euclidiana
        // public static double CalcularDistanciaEuclidiana(Ubicacion u1, Ubicacion u2){
        //     if(u1 == null || u2 == null){
        //         return -1.0;
        //     }
        //     return Math.sqrt(Math.pow(u1.getX() - u2.getX(), 2) + Math.pow(u1.getY() - u2.getY(), 2));
        // }
    
      
    
}
