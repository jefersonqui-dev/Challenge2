package com.jquiguantar.ues.utils;
//Esta clase simple representará un punto en el mapa, con coordenadas geográficas.
public class Ubicacion {
    //atributos
    private int x;  
    private int y;

    //constructor
    public Ubicacion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //getters y setters
    public int getX() {
        return x;
    }
    public int getY(){
        return y;
    }
    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ubicacion ubicacion = (Ubicacion) o;
        return x == ubicacion.x && y == ubicacion.y;
    }
    @Override
    public int hashCode()
    {
        int result = x;
        result = 31 * result + y;
        return result;  
    }

}