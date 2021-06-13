package com.example.glicodexvo1.Models;

public class Estudios {
    private int idEstudioi;
    private String tipo, observaciones, medida;

    public Estudios(int idEstudioi, String tipo, String observaciones, String medida) {
        this.idEstudioi = idEstudioi;
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.medida = medida;
    }

    public int getIdEstudioi() {
        return idEstudioi;
    }

    public void setIdEstudioi(int idEstudioi) {
        this.idEstudioi = idEstudioi;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    @Override
    public String toString() {
        return tipo;
    }
}
