package com.example.glicodexvo1.Models;

public class Horario {
    private int idHorario;
    private String horario;

    public Horario(int idHorario, String horario) {
        this.idHorario = idHorario;
        this.horario = horario;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }


    @Override
    public String toString() {
        return horario;
    }
}
