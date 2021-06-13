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
            color = "#b5a61f";
        }

        if(idCategoria == 2)
        {
            color = "#ffb300";
        }

        if(idCategoria == 3)
        {
            color = "#0c8a17";
        }

        if(idCategoria == 4)
        {
            color = "#ff001e";
        }

        if(idCategoria == 5)
        {
            color = "#f200ff";
        }

        if(idCategoria == 6)
        {
            color = "#734108";
        }

        if(idCategoria == 7)
        {
            color = "#9500ff";
        }

        if(idCategoria == 8)
        {
            color = "#24ced1";
        }

        if(idCategoria == 9)
        {
            color = "#ed6666";
        }

        if(idCategoria == 10)
        {
            color = "#000000";
        }

        return color;
    }
}
