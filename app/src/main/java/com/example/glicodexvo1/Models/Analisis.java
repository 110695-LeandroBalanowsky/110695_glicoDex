package com.example.glicodexvo1.Models;

public class Analisis {
    private int idAnalisis;
    private String fecha;
    private double valor;
    private int idEstudio, idUsuario;

    public Analisis(int idAnalisis, String fecha, double valor, int idEstudio, int idUsuario) {
        this.idAnalisis = idAnalisis;
        this.fecha = fecha;
        this.valor = valor;
        this.idEstudio = idEstudio;
        this.idUsuario = idUsuario;
    }

    public Analisis(String fecha, double valor, int idEstudio, int idUsuario) {
        this.fecha = fecha;
        this.valor = valor;
        this.idEstudio = idEstudio;
        this.idUsuario = idUsuario;
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

    public int getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(int idEstudio) {
        this.idEstudio = idEstudio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
