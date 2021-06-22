package com.example.glicodexvo1.Models;

public class Control {
    private int idControl;
    private int valor;
    private String fecha;
    private String hora;
    private int horario;
    private String comentarios;
    private int dosis;
    private String color;
    private int idUsuario;

    public Control(int valor, String fecha, String hora, int horario, String comentarios, int idUsuario) {
        this.valor = valor;
        this.fecha = fecha;
        this.hora = hora;
        this.horario = horario;
        this.comentarios = comentarios;
        this.idUsuario = idUsuario;
    }

    public Control(int valor, String fecha, String hora, int horario, String comentarios, int dosis, int idUsuario) {
        this.valor = valor;
        this.fecha = fecha;
        this.hora = hora;
        this.horario = horario;
        this.comentarios = comentarios;
        this.dosis = dosis;
        this.idUsuario = idUsuario;
    }

    public Control(int idControl, int valor, String hora, String fecha, int horario, String comentarios, int dosis, int idUsuario) {
        this.idControl = idControl;
        this.valor = valor;
        this.hora = hora;
        this.fecha = fecha;
        this.horario = horario;
        this.comentarios = comentarios;
        this.dosis = dosis;
        this.idUsuario = idUsuario;
        DefinirColor();

    }
    public Control(int idControl, int valor, String hora, String fecha, int horario, String comentarios) {
        this.idControl = idControl;
        this.valor = valor;
        this.hora = hora;
        this.fecha = fecha;
        this.horario = horario;
        this.comentarios = comentarios;
        DefinirColor();
    }

    public int getIdControl() {
        return idControl;
    }

    public void setIdControl(int idControl) {
        this.idControl = idControl;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    private void DefinirColor()
    {
        if(valor < 70)
        {
            color = "#0ec2bc";
        }
        if (valor >= 70 && valor <=140)
        {
            color ="#0db829";
        }
        if (valor > 140)
        {
            color = "#ff0000";
        }
    }



}
