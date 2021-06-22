package com.example.glicodexvo1.Models;

public class DosisDiariaHorario {
    private int idDosisDiaria;
    private String tipoIns;
    private int dosis;
    private String horario;
    private int idUsuario;

    public DosisDiariaHorario(int idDosisDiaria, String tipoIns, int dosis, String horario, int idUsuario) {
        this.idDosisDiaria = idDosisDiaria;
        tipoIns = tipoIns;
        this.dosis = dosis;
        this.horario = horario;
        this.idUsuario = idUsuario;
    }

    public int getIdDosisDiaria() {
        return idDosisDiaria;
    }

    public void setIdDosisDiaria(int idDosisDiaria) {
        this.idDosisDiaria = idDosisDiaria;
    }

    public String gettipoIns() {
        return tipoIns;
    }

    public void setTipoIns(String tipoIns) {
        tipoIns = tipoIns;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
