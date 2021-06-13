package com.example.glicodexvo1.Models;

import java.io.Serializable;

public class Alimentos implements Serializable {
    private int id;
    private String alimento;
    private String medida;
    private double carbo;
    private double calo;
    private String ig;
    private int idCategoria;
    private String color;

    public Alimentos(int id, String alimento, String medida, double carbo, double calo, String ig, int idCategoria) {
        this.id = id;
        this.alimento = alimento;
        this.medida = medida;
        this.carbo = carbo;
        this.calo = calo;
        this.ig = ig;
        this.idCategoria = idCategoria;
        setColor();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public double getCarbo() {
        return carbo;
    }

    public void setCarbo(double carbo) {
        this.carbo = carbo;
    }

    public double getCalo() {
        return calo;
    }

    public void setCalo(double calo) {
        this.calo = calo;
    }

    public String getIg() {
        return ig;
    }

    public void setIg(String ig) {
        this.ig = ig;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getColor() {
        return color;
    }

    public String setColor() {
        if (ig == "Bajo")
        {
            color="#32a852";
        }
        if (ig == "Medio")
        {
            color="#b0ba22";
        }
        if (ig == "Alto")
        {
            color="#ff001e";
        }
        return color;

    }
}
