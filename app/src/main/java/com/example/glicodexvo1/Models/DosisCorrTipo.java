package com.example.glicodexvo1.Models;

public class DosisCorrTipo {
    private int idDosisCorr, desde, hasta, dosis;
    String tipoIns;
    int idUsuario;

    public DosisCorrTipo(int idDosisCorr, int desde, int hasta, int dosis, String tipoIns, int idUsuario) {
        this.idDosisCorr = idDosisCorr;
        this.desde = desde;
        this.hasta = hasta;
        this.dosis = dosis;
        this.tipoIns = tipoIns;
        this.idUsuario = idUsuario;
    }

    public int getIdDosisCorr() {
        return idDosisCorr;
    }

    public void setIdDosisCorr(int idDosisCorr) {
        this.idDosisCorr = idDosisCorr;
    }

    public int getDesde() {
        return desde;
    }

    public void setDesde(int desde) {
        this.desde = desde;
    }

    public int getHasta() {
        return hasta;
    }

    public void setHasta(int hasta) {
        this.hasta = hasta;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public String getTipoIns() {
        return tipoIns;
    }

    public void setTipoIns(String tipoIns) {
        this.tipoIns = tipoIns;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        String resultado = "";
        if(hasta != 999)
        {
            resultado = "Desde " + desde + " hasta " + hasta;
        }
        if(hasta == 999)
        {
            resultado = "Mas de " + desde;
        }
        return resultado;
    }
}
