package com.jquiguantar.ues.model.util;

public class Ubicacion {
    private double latitud;
    private double longitud;

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public double distanciaKm(Ubicacion destino) {
        final double RADIO_TIERRA_KM = 6341.0;
        double dLat = Math.toRadians(destino.latitud - this.latitud);
        double dLon = Math.toRadians(destino.longitud - this.longitud);
        double lat1Rad = Math.toRadians(this.latitud);
        double lat2Rad = Math.toRadians(destino.latitud);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(latitud);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitud);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ubicacion other = (Ubicacion) obj;
        if (Double.doubleToLongBits(latitud) != Double.doubleToLongBits(other.latitud))
            return false;
        if (Double.doubleToLongBits(longitud) != Double.doubleToLongBits(other.longitud))
            return false;
        return true;
    }

}
