package com.example.glicodexvo1.Models;

public class DosisDiaria {
    private int idDosisDiaria, idTipoIns, idHorario, idUsuario;

    public DosisDiaria(int idDosisDiaria, int idTipoIns, int idHorario, int idUsuario) {
        this.idDosisDiaria = idDosisDiaria;
        this.idTipoIns = idTipoIns;
        this.idHorario = idHorario;
        this.idUsuario = idUsuario;
    }

    public int getIdDosisDiaria() {
        return idDosisDiaria;
    }

    public void setIdDosisDiaria(int idDosisDiaria) {
        this.idDosisDiaria = idDosisDiaria;
    }

    public int getIdTipoIns() {
        return idTipoIns;
    }

    public void setIdTipoIns(int idTipoIns) {
        this.idTipoIns = idTipoIns;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
