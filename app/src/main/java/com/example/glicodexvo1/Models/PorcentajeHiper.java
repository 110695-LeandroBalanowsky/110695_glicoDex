package com.example.glicodexvo1.Models;

public class PorcentajeHiper {
    String horario;
    float porcentaje;

    public PorcentajeHiper(String horario, float porcentaje) {
        this.horario = horario;
        this.porcentaje = porcentaje;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }
}
