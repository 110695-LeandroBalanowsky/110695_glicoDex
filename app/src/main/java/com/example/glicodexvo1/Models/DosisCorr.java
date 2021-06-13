package com.example.glicodexvo1.Models;

public class DosisCorr {
    private int idDosisCorr;
    private int desde, hasta, idTipoIns, idUsuario;

    public DosisCorr(int idDosisCorr, int desde, int hasta, int idTipoIns, int idUsuario) {
        this.idDosisCorr = idDosisCorr;
        this.desde = desde;
        this.hasta = hasta;
        this.idTipoIns = idTipoIns;
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

    public int getIdTipoIns() {
        return idTipoIns;
    }

    public void setIdTipoIns(int idTipoIns) {
        this.idTipoIns = idTipoIns;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
