package com.example.luisfelix.gym;

//Clase que contiene las características que serán mostradas en la vista preliminar de cada rutina. También contiene Getters y Setters.

public class Rutina {
    int rutinaId;
    String rutinaNombre, descripcionRutina;

    public Rutina (String rutinaNombre, String descripcionRutina) {
        this.rutinaNombre = rutinaNombre;
        this.descripcionRutina = descripcionRutina;
    }

    public String getRutinaNombre() {
        return rutinaNombre;
    }

    public void setRutinaNombre(String rutinaNombre) {
        this.rutinaNombre = rutinaNombre;
    }

    public String getDescripcionRutina() {
        return descripcionRutina;
    }

    public void setDescripcionRutina(String descripcionRutina) {
        this.descripcionRutina = descripcionRutina;
    }
}