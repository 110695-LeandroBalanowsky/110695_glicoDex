package com.example.glicodexvo1.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.glicodexvo1.Models.Alimentos;
import com.example.glicodexvo1.Models.Analisis;
import com.example.glicodexvo1.Models.AnalisisEstudios;
import com.example.glicodexvo1.Models.CategoriasAlimentos;
import com.example.glicodexvo1.Models.Control;
import com.example.glicodexvo1.Models.ControlHorario;
import com.example.glicodexvo1.Models.DosisCorr;
import com.example.glicodexvo1.Models.DosisCorrTipo;
import com.example.glicodexvo1.Models.DosisDiaria;
import com.example.glicodexvo1.Models.DosisDiariaHorario;
import com.example.glicodexvo1.Models.Estudios;
import com.example.glicodexvo1.Models.Horario;
import com.example.glicodexvo1.Models.PorcentajeHiper;
import com.example.glicodexvo1.Models.PromediosControles;
import com.example.glicodexvo1.Models.TipoInsulina;
import com.example.glicodexvo1.Models.Usuario;

import java.util.ArrayList;

public class AccesoBD {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static AccesoBD instance;
    Cursor c = null;

    private AccesoBD(Context context)
    {
        this.openHelper = new DbHelper(context);

    }

    public static AccesoBD getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new AccesoBD(context);
        }
        return instance;
    }

    public void Open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void Close()
    {
        if (db != null)
        {
            this.db.close();
        }
    }

    public ArrayList<CategoriasAlimentos> getCategorias()
    {
        ArrayList<CategoriasAlimentos> lista = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT * FROM Categoria", null);
        while (c.moveToNext())
        {
               int idCategoria = c.getInt(0);
               String categoria = c.getString(1);
               CategoriasAlimentos ca = new CategoriasAlimentos(idCategoria, categoria);
               lista.add(ca);
        }
        Close();

        return lista;
    }

    public ArrayList<Alimentos> getAlimentos(int idCat) {
       ArrayList<Alimentos> lista = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT * FROM Alimentos WHERE idCategoria = " + idCat, null);
        while (c.moveToNext())
        {
            int idAlimento = c.getInt(0);
            String alimento = c.getString(1);
            String medida = c.getString(2);
            double carbo = c.getDouble(3);
            double calo = c.getDouble(4);
            String ig = c.getString(5);
            int idCategoria = c.getInt(6);

            Alimentos ali = new Alimentos(idAlimento, alimento, medida, carbo, calo, ig, idCategoria);
            lista.add(ali);
        }
        Close();

        return lista;
    }
    public Usuario getUsuario() {
        Usuario user = null;

        Open();

        c=db.rawQuery("SELECT * FROM Usuario", null);
        if (c.moveToNext()) {
            int idUsuario = c.getInt(0);
            String nombre = c.getString(1);
            String apellido = c.getString(2);
            double altura = c.getDouble(3);
            double peso = c.getDouble(4);

            user = new Usuario(idUsuario, nombre, apellido, altura, peso);
        }
        Close();

        return user;
    }
    public Long setUsuario(Usuario user)
    {

        Open();
        ContentValues values = new ContentValues();
        values.put("Nombre", user.getNombre());
        values.put("Apellido", user.getApellido());
        values.put("Altura", user.getAltura());
        values.put("Peso", user.getPeso());

        Long resultante = db.insert("Usuario", "idUsuario", values);
        Close();
        return resultante;
    }


    public Usuario getUsuarioxId(int idUs)
    {
        Usuario user = null;

        Open();

        c=db.rawQuery("SELECT * FROM Usuario WHERE idUsuario =" + idUs, null);
        if (c.moveToNext()) {
            int idUsuario = c.getInt(0);
            String nombre = c.getString(1);
            String apellido = c.getString(2);
            double altura = c.getDouble(3);
            double peso = c.getDouble(4);

            user = new Usuario(idUsuario, nombre, apellido, altura, peso);
        }
        Close();

        return user;
    }
    public boolean modUsuario(Usuario usuario)
    {
        boolean resultado = false;
        Open();
        String[] idUsuario= {String.valueOf(usuario.getIdUsuario())};
        ContentValues values = new ContentValues();
        values.put("Nombre", usuario.getNombre());
        values.put("Apellido", usuario.getApellido());
        values.put("Altura", usuario.getAltura());
        values.put("Peso", usuario.getPeso());

        db.update("Usuario", values, "idUsuario=?",idUsuario);
        Close();
        resultado = true;
        return resultado;
    }

    public Long setControl(Control control)
    {

        Open();
        ContentValues values = new ContentValues();
        values.put("Valor", control.getValor());
        values.put("Hora", control.getHora());
        values.put("Fecha", control.getFecha());
        values.put("idHorario", control.getHorario());
        values.put("Dosis", control.getDosis());
        values.put("Comentarios", control.getComentarios());
        values.put("idUsuario", control.getIdUsuario());

        Long resultante = db.insert("Controles", "idControl", values);
        Close();
        return resultante;
    }
    public boolean modControl(Control control)
    {
        boolean resultado = false;
        Open();
        String[] idControl= {String.valueOf(control.getIdControl())};
        ContentValues values = new ContentValues();
        values.put("Valor", control.getValor());
        values.put("Hora", control.getHora());
        values.put("Fecha", control.getFecha());
        values.put("idHorario", control.getHorario());
        values.put("Dosis", 0);
        values.put("Comentarios", control.getComentarios());

        db.update("Controles", values, "idControl=?",idControl);
        Close();
        resultado = true;
        return resultado;
    }

    public boolean elmControl(int idCon)
    {
        boolean resultado = false;
        Open();
        String[] idControl= {String.valueOf(idCon)};

        db.delete("Controles", "idControl=?",idControl);
        Close();
        resultado = true;
        return resultado;
    }

    public ArrayList<ControlHorario> getControles(String sql)
    {
        ArrayList<ControlHorario> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery(sql, null);
        while (c.moveToNext()) {
            int idControl = c.getInt(0);
            int valor = c.getInt(1);
            String hora = c.getString(2);
            String fecha = c.getString(3);
            String horario = c.getString(4);
            int dosis = c.getInt(5);
            String comentarios= c.getString(6);
            int idUsuario=c.getInt(7);

            ControlHorario con = new ControlHorario(idControl, valor, hora, fecha, horario, comentarios, dosis,idUsuario);
            resultado.add(con);
        }
        Close();

        return resultado;
    }
    public ArrayList<ControlHorario> getControlesBuscar(String fec)
    {
        ArrayList<ControlHorario> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT idControl, valor, Hora, fecha, h.Horario, Dosis, Comentarios, idUsuario\n" +
                "FROM Controles c JOIN Horarios h ON c.idHorario= h.idHorarios\n" +
                "WHERE date(fecha) = date('"+ fec +"')\n" +
                "ORDER BY date(fecha) DESC, time(hora) DESC", null);
        while (c.moveToNext()) {
            int idControl = c.getInt(0);
            int valor = c.getInt(1);
            String hora = c.getString(2);
            String fecha = c.getString(3);
            String horario = c.getString(4);
            int dosis = c.getInt(5);
            String comentarios= c.getString(6);
            int idUsuario=c.getInt(7);

            ControlHorario con = new ControlHorario(idControl, valor, hora, fecha, horario, comentarios, dosis,idUsuario);
            resultado.add(con);
        }
        Close();

        return resultado;
    }
    public Control getControl(int idCon)
    {
        Control resultado = null;

        Open();

        c=db.rawQuery("SELECT * FROM Controles WHERE idControl = " + idCon, null);
        while (c.moveToNext()) {
            int idControl = c.getInt(0);
            int valor = c.getInt(1);
            String hora = c.getString(2);
            String fecha = c.getString(3);
            int horario = c.getInt(4);
            int dosis = c.getInt(5);
            String comentarios= c.getString(6);
            int idUsuario=c.getInt(7);

            resultado = new Control(idControl, valor, hora, fecha, horario, comentarios, dosis,idUsuario);

        }
        Close();

        return resultado;
    }
    public ArrayList<Horario> getHorarios()
    {
        ArrayList<Horario> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT * FROM Horarios;", null);
        while (c.moveToNext()) {
            int idHorario = c.getInt(0);
            String horario = c.getString(1);


            Horario aux = new Horario(idHorario,horario);
            resultado.add(aux);
        }
        Close();

        return resultado;
    }

    public ArrayList<PromediosControles> getPromediosSemanales() {
        ArrayList<PromediosControles> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT fecha, avg(valor)\n" +
                "FROM Controles\n" +
                "WHERE date(fecha) >= date('now', '-7 days')\n" +
                "group by fecha\n" +
                "ORDER by fecha DESC;", null);

        while (c.moveToNext()) {
            String fecha = c.getString(0);
            float promedioValor = c.getFloat(1);

            resultado.add(new PromediosControles(fecha, promedioValor));
        }
        Close();

        return resultado;
    }
    public ArrayList<PromediosControles> getPromediosQuincenal() {
        ArrayList<PromediosControles> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT fecha, avg(valor)\n" +
                "FROM Controles\n" +
                "WHERE date(fecha) >= date('now', '-15 days')\n" +
                "group by fecha\n" +
                "ORDER by fecha DESC;", null);

        while (c.moveToNext()) {
            String fecha = c.getString(0);
            float promedioValor = c.getFloat(1);

            resultado.add(new PromediosControles(fecha, promedioValor));
        }
        Close();

        return resultado;
    }
    public ArrayList<PromediosControles> getPromediosMensual() {
        ArrayList<PromediosControles> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT fecha, avg(valor)\n" +
                "FROM Controles\n" +
                "WHERE date(fecha) >= date('now', '-30 days')\n" +
                "group by fecha\n" +
                "ORDER by fecha DESC;", null);

        while (c.moveToNext()) {
            String fecha = c.getString(0);
            float promedioValor = c.getFloat(1);

            resultado.add(new PromediosControles(fecha, promedioValor));
        }
        Close();

        return resultado;
    }


    public float getPromedioTotalSemanal() {
        float resultado = 0;

        Open();

        c=db.rawQuery("SELECT avg(valor)\n" +
                "FROM Controles\n" +
                "WHERE date(fecha) >= date('now', '-7 days')\n" +
                "ORDER by fecha DESC;", null);

        if (c.moveToNext()) {
            resultado = c.getFloat(0);
        }
        Close();

        return resultado;
    }
    public float getPromedioTotalQuincenal() {
        float resultado = 0;

        Open();

        c=db.rawQuery("SELECT avg(valor)\n" +
                "FROM Controles\n" +
                "WHERE date(fecha) >= date('now', '-15 days')\n" +
                "ORDER by fecha DESC;", null);

        if (c.moveToNext()) {
            resultado = c.getFloat(0);
        }
        Close();

        return resultado;
    }
    public float getPromedioTotalMensual() {
        float resultado = 0;

        Open();

        c=db.rawQuery("SELECT avg(valor)\n" +
                "FROM Controles\n" +
                "WHERE date(fecha) >= date('now', '-30 days')\n" +
                "ORDER by fecha DESC;", null);

        if (c.moveToNext()) {
            resultado = c.getFloat(0);
        }
        Close();

        return resultado;
    }
    public ArrayList<PorcentajeHiper> getPorcentajesHiperSemanales() {
        ArrayList<PorcentajeHiper> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT Horario, (count(*) * 100) /(SELECT count(*) FROM Controles WHERE valor > 140 AND date(fecha) >= date('now', '-7 days')) as Prc\n" +
                "FROM Controles c JOIN Horarios h on c.idHorario = h.idHorarios\n" +
                "WHERE valor > 140\n" +
                "AND date(fecha) >= date('now', '-7 days')\n" +
                "AND idUsuario = 1\n" +
                "GROUP by idHorario;", null);

        while (c.moveToNext()) {
            String horario = c.getString(0);
            float porcentaje = c.getFloat(1);

            resultado.add(new PorcentajeHiper(horario, porcentaje));
        }
        Close();

        return resultado;
    }
    public ArrayList<PorcentajeHiper> getPorcentajesHiperQuincenales() {
        ArrayList<PorcentajeHiper> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT Horario, (count(*) * 100) /(SELECT count(*) FROM Controles WHERE valor > 140 AND date(fecha) >= date('now', '-15 days')) as Prc\n" +
                "FROM Controles c JOIN Horarios h on c.idHorario = h.idHorarios\n" +
                "WHERE valor > 140\n" +
                "AND date(fecha) >= date('now', '-15 days')\n" +
                "AND idUsuario = 1\n" +
                "GROUP by idHorario;", null);

        while (c.moveToNext()) {
            String horario = c.getString(0);
            float porcentaje = c.getFloat(1);

            resultado.add(new PorcentajeHiper(horario, porcentaje));
        }
        Close();

        return resultado;
    }
    public ArrayList<PorcentajeHiper> getPorcentajesHiperMensual() {
        ArrayList<PorcentajeHiper> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT Horario, (count(*) * 100) /(SELECT count(*) FROM Controles WHERE valor > 140 AND date(fecha) >= date('now', '-30 days')) as Prc\n" +
                "FROM Controles c JOIN Horarios h on c.idHorario = h.idHorarios\n" +
                "WHERE valor > 140\n" +
                "AND date(fecha) >= date('now', '-30 days')\n" +
                "AND idUsuario = 1\n" +
                "GROUP by idHorario;", null);

        while (c.moveToNext()) {
            String horario = c.getString(0);
            float porcentaje = c.getFloat(1);

            resultado.add(new PorcentajeHiper(horario, porcentaje));
        }
        Close();

        return resultado;
    }
    public ArrayList<Estudios> getEstudios()
    {
        ArrayList<Estudios> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT * FROM Estudios;", null);
        while (c.moveToNext()) {
            int idEstudio = c.getInt(0);
            String tipo = c.getString(1);
            String observaciones = c.getString(2);
            String medida = c.getString(3);



            Estudios aux = new Estudios(idEstudio, tipo, observaciones, medida);
            resultado.add(aux);
        }
        Close();

        return resultado;
    }
    public Long setAnalisis(Analisis analisis)
    {

        Open();
        ContentValues values = new ContentValues();

        values.put("Fecha", analisis.getFecha());
        values.put("Valor", analisis.getValor());
        values.put("idEstudio", analisis.getIdEstudio());
        values.put("idUsuario", analisis.getIdUsuario());

        Long resultante = db.insert("Analisis", "idAnalisis", values);
        Close();
        return resultante;
    }
    public boolean modAnalisis(Analisis analisis)
    {
        boolean resultado = false;
        Open();
        String[] idAnalisis= {String.valueOf(analisis.getIdAnalisis())};
        ContentValues values = new ContentValues();
        values.put("Fecha", analisis.getFecha());
        values.put("Valor", analisis.getValor());
        values.put("idEstudio", analisis.getIdEstudio());
        values.put("idUsuario", analisis.getIdUsuario());

        db.update("Analisis", values, "idAnalisis=?",idAnalisis);
        Close();
        resultado = true;
        return resultado;
    }

    public boolean elmAnalisis(int idAna)
    {
        boolean resultado = false;
        Open();
        String[] idAnalisis= {String.valueOf(idAna)};

        db.delete("Analisis", "idAnalisis=?",idAnalisis);
        Close();
        resultado = true;
        return resultado;
    }
    public ArrayList<String> getFechasAnalisis(String fch) {
        ArrayList<String> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT DISTINCT fecha FROM Analisis\n" +
                "WHERE strftime('%Y',fecha) = '"+ fch +"'\n" +
                "ORDER by fecha DESC;", null);
        //c=db.rawQuery("SELECT DISTINCT fecha FROM Analisis", null);

        while (c.moveToNext()) {
            String fecha = c.getString(0);
            resultado.add(fecha);
        }
        Close();

        return resultado;
    }
    public ArrayList<AnalisisEstudios> getAnalisisEstudios(String fecAnal)
    {
        ArrayList<AnalisisEstudios> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT idAnalisis, fecha, valor, medida, Tipo, Observaciones, idUsuario\n" +
                "FROM Analisis a \n" +
                "JOIN Estudios e \n" +
                "ON a.idEstudio = e.idEstudios\n" +
                "WHERE date(fecha) = date('"+fecAnal+"')\n" +
                "ORDER by idEstudio;", null);

        while (c.moveToNext()) {

            int idAnalisis = c.getInt(0);
            String fecha = c.getString(1);
            double valor = c.getDouble(2);
            String medida = c.getString(3);
            String estudio = c.getString(4);
            String observaciones = c.getString(5);
            int idUsuario = c.getInt(6);



            AnalisisEstudios aux = new AnalisisEstudios(idAnalisis, fecha, valor, medida, estudio, observaciones, idUsuario);
            resultado.add(aux);
        }
        Close();

        return resultado;
    }
    public ArrayList<String> getAnioAnalisis() {
        ArrayList<String> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT DISTINCT strftime('%Y',fecha) FROM Analisis\n" +
                "ORDER by fecha DESC;", null);

        while (c.moveToNext()) {
            String anio = c.getString(0);
            resultado.add(anio);
        }
        Close();

        return resultado;
    }
    public Analisis getAnalisis(int idAn)
    {
        Analisis resultado = null;

        Open();

        c=db.rawQuery("SELECT * FROM Analisis WHERE idAnalisis = " + idAn, null);
        while (c.moveToNext()) {
            int idAnalisis = c.getInt(0);
            String fecha = c.getString(1);
            int valor = c.getInt(2);
            int idEstudio = c.getInt(3);
            int idUsuario=c.getInt(4);

            resultado = new Analisis(idAnalisis, fecha, valor, idEstudio,idUsuario);

        }
        Close();

        return resultado;
    }
    public ArrayList<AnalisisEstudios> getAnalisisEstudiosReporte(int idEs, String anio)
    {
        ArrayList<AnalisisEstudios> resultado = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT idAnalisis, fecha, valor, medida, Tipo, Observaciones, idUsuario\n" +
                "FROM Analisis a \n" +
                "JOIN Estudios e \n" +
                "ON a.idEstudio = e.idEstudios\n" +
                "WHERE strftime('%Y',fecha) = '"+ anio +"'\n" +
                "AND idEstudio = "+ idEs +"\n" +
                "ORDER by idEstudio", null);

        while (c.moveToNext()) {

            int idAnalisis = c.getInt(0);
            String fecha = c.getString(1);
            double valor = c.getDouble(2);
            String medida = c.getString(3);
            String estudio = c.getString(4);
            String observaciones = c.getString(5);
            int idUsuario = c.getInt(6);



            AnalisisEstudios aux = new AnalisisEstudios(idAnalisis, fecha, valor, medida, estudio, observaciones, idUsuario);
            resultado.add(aux);
        }
        Close();

        return resultado;
    }
    public ArrayList<TipoInsulina> getTiposInsulina()
    {
        ArrayList<TipoInsulina> lista = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT * FROM TiposInsulina", null);
        while (c.moveToNext())
        {
            int idTipoInsulina = c.getInt(0);
            String tipo = c.getString(1);
            String info = c.getString(2);

            TipoInsulina tipoInsulina = new TipoInsulina(idTipoInsulina, tipo, info);
            lista.add(tipoInsulina);
        }
        Close();

        return lista;
    }
    public Long setDosisDiaria(DosisDiaria dos)
    {

        Open();
        ContentValues values = new ContentValues();

        values.put("idTipoIns", dos.getIdTipoIns());
        values.put("Dosis", dos.getDosis());
        values.put("idHorario", dos.getIdHorario());
        values.put("idUsuario", dos.getIdUsuario());

        Long resultante = db.insert("DosisDiaria", "idDosisDiaria", values);
        Close();
        return resultante;
    }
    public boolean elmDosisDiaria(int idDos)
    {
        boolean resultado = false;
        Open();
        String[] idDosisDiaria= {String.valueOf(idDos)};

        db.delete("DosisDiaria", "idDosisDiaria=?",idDosisDiaria);
        Close();
        resultado = true;
        return resultado;
    }
    public ArrayList<TipoInsulina> getTiposInsulinaDosisDiaria()
    {
        ArrayList<TipoInsulina> lista = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT * \n" +
                "FROM TiposInsulina\n" +
                "WHERE idTipo IN(SELECT idTipoIns FROM DosisDiaria)", null);
        while (c.moveToNext())
        {
            int idTipoInsulina = c.getInt(0);
            String tipo = c.getString(1);
            String info = c.getString(2);

            TipoInsulina tipoInsulina = new TipoInsulina(idTipoInsulina, tipo, info);
            lista.add(tipoInsulina);
        }
        Close();

        return lista;
    }
    public ArrayList<DosisDiariaHorario> getDosisDiaria(int idIns)
    {
        ArrayList<DosisDiariaHorario> lista = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT idDosisDiaria, Tipo, Dosis, Horario, idUsuario\n" +
                "FROM DosisDiaria dd JOIN TiposInsulina TI on dd.idTipoIns = ti.idTipo\n" +
                "JOIN Horarios h on dd.idHorario = H.idHorarios\n" +
                "WHERE idTipoIns = "+ idIns +" ORDER by idHorario ASC;", null);
        while (c.moveToNext())
        {
            int idDosisDiaria = c.getInt(0);
            String tipoIns = c.getString(1);;
            int dosis = c.getInt(2);
            String horario = c.getString(3);
            int idUsuario = c.getInt(4);

            DosisDiariaHorario dos = new DosisDiariaHorario(idDosisDiaria, tipoIns, dosis, horario, idUsuario);
            lista.add(dos);
        }
        Close();

        return lista;
    }
    public Long setDosisCorrectiva(DosisCorr dos)
    {

        Open();
        ContentValues values = new ContentValues();

        values.put("Desde", dos.getDesde());
        values.put("Hasta", dos.getHasta());
        values.put("Dosis", dos.getDosis());
        values.put("idTipoIns", dos.getIdTipoIns());
        values.put("idUsuario", dos.getIdUsuario());

        Long resultante = db.insert("DosisCorr", "idDosisCorr", values);
        Close();
        return resultante;
    }
    public ArrayList<DosisCorrTipo> getDosisCorr(int idIns)
    {
        ArrayList<DosisCorrTipo> lista = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT idDosisCorr, Desde, Hasta, Dosis, Tipo, idUsuario\n" +
                "FROM DosisCorr dc JOIN TiposInsulina ti ON dc.idTipoIns = ti.idTipo\n" +
                "WHERE idTipoIns = "+ idIns +" Order by desde ASC;", null);
        while (c.moveToNext())
        {
            int idDosisCorr = c.getInt(0);
            int desde = c.getInt(1);
            int hasta = c.getInt(2);
            int dosis = c.getInt(3);
            String tipoIns = c.getString(4);
            int idUsuario = c.getInt(5);

            DosisCorrTipo dos = new DosisCorrTipo(idDosisCorr, desde, hasta, dosis, tipoIns, idUsuario);
            lista.add(dos);
        }
        Close();

        return lista;
    }
    public boolean elmDosisCorr(int idDos)
    {
        boolean resultado = false;
        Open();
        String[] idDosisCorr= {String.valueOf(idDos)};

        db.delete("DosisCorr", "idDosisCorr=?",idDosisCorr);
        Close();
        resultado = true;
        return resultado;
    }
    public ArrayList<TipoInsulina> getTiposInsulinaDosisCorr()
    {
        ArrayList<TipoInsulina> lista = new ArrayList<>();

        Open();

        c=db.rawQuery("SELECT * FROM TiposInsulina WHERE idTipo IN(SELECT idTipoIns FROM DosisCorr)", null);
        while (c.moveToNext())
        {
            int idTipoInsulina = c.getInt(0);
            String tipo = c.getString(1);
            String info = c.getString(2);

            TipoInsulina tipoInsulina = new TipoInsulina(idTipoInsulina, tipo, info);
            lista.add(tipoInsulina);
        }
        Close();

        return lista;
    }
    public int getDosis(int valor)
    {
        int dosis = 0;
        Open();
        c=db.rawQuery("select Dosis FROM DosisCorr WHERE desde <= "+ valor +" And Hasta >= "+ valor +";", null);

        while (c.moveToNext())
        {
            dosis = c.getInt(0);
        }
        Close();

        return dosis;
    }

}
