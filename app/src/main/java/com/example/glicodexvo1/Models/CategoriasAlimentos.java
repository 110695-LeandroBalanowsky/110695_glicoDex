package com.example.glicodexvo1.Models;

import java.io.Serializable;

public class CategoriasAlimentos implements Serializable {
    private int idCategoria;
    private String categoria;
    private String color;

    public CategoriasAlimentos(int idCategoria, String categoria) {
        this.idCategoria = idCategoria;
        this.categoria = categoria;
        setColor();
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getColor() {
        return color;
    }


    public String setColor()
    {
        if(idCategoria == 1)
        {
            color = "#e6ac00";
        }

        if(idCategoria == 2)
        {
            color = "#ff944d";
        }

        if(idCategoria == 3)
        {
            color = "#70db70";
        }

        if(idCategoria == 4)
        {
            color = "#ff001e";
        }

        if(idCategoria == 5)
        {
            color = "#ff80bf";
        }

        if(idCategoria == 6)
        {
            color = "#ff7733";
        }

        if(idCategoria == 7)
        {
            color = "#d98cb3";
        }

        if(idCategoria == 8)
        {
            color = "#66c2ff";
        }

        if(idCategoria == 9)
        {
            color = "#ff9980";
        }

        if(idCategoria == 10)
        {
            color = "#b2b266";
        }

        return color;
    }
}
