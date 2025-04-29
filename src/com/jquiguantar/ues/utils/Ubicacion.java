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
    
}