package com.example.glicodexvo1.Models;

public class AnalisisEstudios {
    private int idAnalisis;
    private String fecha;
    private double valor;
    private String medida;
    private String estudio;
    private String observaciones;
    private int idUsuario;

    public AnalisisEstudios(int idAnalisis, String fecha, double valor, String medida, String estudio, String observaciones, int idUsuario) {
        this.idAnalisis = idAnalisis;
        this.fecha = fecha;
        this.valor = valor;
        this.medida = medida;
        this.estudio = estudio;
        this.observaciones = observaciones;
        this.idUsuario = idUsuario;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(int idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
