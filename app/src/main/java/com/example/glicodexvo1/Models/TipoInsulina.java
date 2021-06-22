package com.example.glicodexvo1.Models;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.example.glicodexvo1.R;

public class TipoInsulina {
    private int idTipoInsulina;
    private String tipo, info;
    private String color;

    public TipoInsulina(int idTipoInsulina, String tipo, String info) {
        this.idTipoInsulina = idTipoInsulina;
        this.tipo = tipo;
        this.info = info;
        definirColor();
    }

    public int getIdTipoInsulina() {
        return idTipoInsulina;
    }

    public void setIdTipoInsulina(int idTipoInsulina) {
        this.idTipoInsulina = idTipoInsulina;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getColor() {
        return color;
    }

    public void definirColor()
    {
        if (idTipoInsulina == 1)
        {
            color = "#ffb366";
        }
        if (idTipoInsulina == 2)
        {
            color = "#ffff66";
        }
        if (idTipoInsulina == 3)
        {
            color = "#85e085";
        }
        if (idTipoInsulina == 4)
        {
            color = "#668cff";
        }
        if (idTipoInsulina == 5)
        {
            color = "#ff8000";
        }
    }

    @NonNull
    @Override
    public String toString() {
        return tipo;
    }
}
