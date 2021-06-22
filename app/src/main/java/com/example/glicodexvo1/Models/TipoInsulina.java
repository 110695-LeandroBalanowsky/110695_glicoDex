package com.example.glicodexvo1.Models;

import androidx.annotation.NonNull;

public class TipoInsulina {
    private int idTipoInsulina;
    private String tipo, info;

    public TipoInsulina(int idTipoInsulina, String tipo, String info) {
        this.idTipoInsulina = idTipoInsulina;
        this.tipo = tipo;
        this.info = info;
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

    @NonNull
    @Override
    public String toString() {
        return tipo;
    }
}
