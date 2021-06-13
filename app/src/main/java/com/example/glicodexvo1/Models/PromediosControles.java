package com.example.glicodexvo1.Models;

public class PromediosControles {
    String fecha;
    float promedio;

    public PromediosControles(String fecha, float promedio) {
        this.fecha = fecha;
        this.promedio = promedio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPromedio() {
        return promedio;
    }

    public void setPromedio(float promedio) {
        this.promedio = promedio;
    }
}
