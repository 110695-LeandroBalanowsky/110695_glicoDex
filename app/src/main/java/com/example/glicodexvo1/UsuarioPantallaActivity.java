package com.example.glicodexvo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.glicodexvo1.Fragments.InsulinaCorrCargar;
import com.example.glicodexvo1.Fragments.InsulinaDiariaCargar;
import com.example.glicodexvo1.Fragments.UsuarioModificar;
import com.example.glicodexvo1.Models.DosisCorrTipo;
import com.example.glicodexvo1.Models.DosisDiariaHorario;
import com.example.glicodexvo1.Models.TipoInsulina;
import com.example.glicodexvo1.Models.Usuario;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ListAdapterDosisCorr;
import com.example.glicodexvo1.Utilidades.ListAdapterDosisDiaria;

import java.util.ArrayList;

public class UsuarioPantallaActivity extends AppCompatActivity implements UsuarioModificar.ModificarUsuarioListener, InsulinaDiariaCargar.CargarDosisDiariaListener, InsulinaCorrCargar.CargarDosisCorrListener {
    //Datos del usuario
    TextView nombreCompleto, altura, peso;
    ImageView imgPerfil;
    AccesoBD bd;
    Usuario user = null;
    //Dosis Diarias
    TextView mensajeDiaria;
    ArrayList<Object> listaDiaria;
    ArrayList<TipoInsulina> tipInsDiaria;
    ArrayList<DosisDiariaHorario> dosisDiaria;
    private RecyclerView recyclerViewDiario;
    private ListAdapterDosisDiaria listAdapterDiario;
    //Dosis Correctivas
    TextView mensajeCorr;
    ArrayList<Object> listaCorr;
    ArrayList<TipoInsulina> tipInsCorr;
    ArrayList<DosisCorrTipo> dosisCorr;
    private RecyclerView recyclerViewCorr;
    private ListAdapterDosisCorr listAdapterCorr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_pantalla);
        //Usuario
        nombreCompleto = findViewById(R.id.lblNombreCompleto);
        imgPerfil = findViewById(R.id.imgPerfil);
        imgPerfil.setImageResource(R.drawable.avatar);
        altura= findViewById(R.id.lblAltura);
        peso = findViewById(R.id.lblPeso);
        bd = AccesoBD.getInstance(getApplicationContext());
        cargarDatos();
        //Dosis Diarias
        mensajeDiaria = findViewById(R.id.txtMensajeDiara2);
        tipInsDiaria = bd.getTiposInsulinaDosisDiaria();
        listaDiaria = new ArrayList<>();
        recyclerViewDiario = findViewById(R.id.recDosisDiaria);
        //recyclerViewDiario.setNestedScrollingEnabled(false);
        InitViewsDiarias();
        InitValuesDiarias();
        //Dosis correccion
        mensajeCorr = findViewById(R.id.txtMensajeCorrecciones);
        tipInsCorr = bd.getTiposInsulinaDosisCorr();
        listaCorr = new ArrayList<>();
        recyclerViewCorr = findViewById(R.id.recDosisCorr);
        InitViewsCorr();
        InitValuesCorr();

    }
    //datos del usuario
    public void cargarDatos()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        int idUs = sharedPreferences.getInt("idUsuario", 0);

        user = bd.getUsuarioxId(idUs);

        nombreCompleto.setText(user.getNombre() + " " + user.getApellido());
        altura.setText(String.valueOf(user.getAltura())+"cm");
        peso.setText(String.valueOf(user.getPeso())+"kg");
    }
    //menu
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.idModificarUsuario:
                ModificarUsuario();
                return true;
            case R.id.idModInsDiaria:
                CarModDosisDiaria();
                return true;
            case R.id.idModInsCorrec:
                CarModDosisCorr();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void ModificarUsuario() {
        UsuarioModificar modificUs = new UsuarioModificar();
        Bundle bundle = new Bundle();
        bundle.putInt("idUs", user.getIdUsuario());
        modificUs.setArguments(bundle);
        modificUs.show(getSupportFragmentManager(), "modificar Usuario");
    }
    public void CarModDosisDiaria() {
        InsulinaDiariaCargar insDia = new InsulinaDiariaCargar();
        Bundle bundle = new Bundle();
        bundle.putInt("idUs", user.getIdUsuario());
        insDia.setArguments(bundle);
        insDia.show(getSupportFragmentManager(), "Cargar dosis diaria");
    }
    private void CarModDosisCorr() {
        InsulinaCorrCargar insCorr = new InsulinaCorrCargar();
        Bundle bundle = new Bundle();
        bundle.putInt("idUs", user.getIdUsuario());
        insCorr.setArguments(bundle);
        insCorr.show(getSupportFragmentManager(), "Cargar dosis diaria");
    }
    //Dosis Diarias
    public void InitValuesDiarias()
    {
        LinearLayoutManager manager;
        manager = new LinearLayoutManager(this);
        recyclerViewDiario.setLayoutManager(manager);
    }
    private void InitViewsDiarias()
    {
        if (!tipInsDiaria.isEmpty())
        {
            mensajeDiaria.setText("");
            for(int i = 0; i < tipInsDiaria.size(); i++)
            {
                listaDiaria.add(tipInsDiaria.get(i));
                dosisDiaria = bd.getDosisDiaria(tipInsDiaria.get(i).getIdTipoInsulina());
                for (int j = 0; j < dosisDiaria.size(); j++)
                {
                    listaDiaria.add(dosisDiaria.get(j));
                }

            }
            listAdapterDiario = new ListAdapterDosisDiaria(listaDiaria);
            recyclerViewDiario.setAdapter(listAdapterDiario);
        }
        else
        {
            mensajeDiaria.setText("No hay dosis cargadas");
        }

    }
    //Dosis Corr
    public void InitValuesCorr()
    {
        LinearLayoutManager manager;
        manager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCorr.setLayoutManager(manager);
    }


    private void InitViewsCorr()
    {
        if (!tipInsCorr.isEmpty())
        {
            mensajeCorr.setText("");
            for(int i = 0; i < tipInsCorr.size(); i++)
            {
                listaCorr.add(tipInsCorr.get(i));
                dosisCorr = bd.getDosisCorr(tipInsCorr.get(i).getIdTipoInsulina());
                for (int j = 0; j < dosisCorr.size(); j++)
                {
                    listaCorr.add(dosisCorr.get(j));
                }

            }
            listAdapterCorr = new ListAdapterDosisCorr(listaCorr);
            recyclerViewCorr.setAdapter(listAdapterCorr);
        }
        else
        {
            mensajeCorr.setText("No hay dosis cargadas");
        }

    }

    //Botones de faq y TyC
    public void pantalla_faq(View vista)
    {
        Intent i = new Intent(this, FaqActivity.class);
        startActivity(i);
    }
    public void pantalla_term(View vista)
    {
        Intent i = new Intent(this, TerminosActivity.class);
        startActivity(i);
    }

    //Listeners
    @Override
    public void RecargarDatos() {
        cargarDatos();
    }

    @Override
    public void RecargarListaDiaria() {
        listaDiaria.clear();
        tipInsDiaria.clear();
        tipInsDiaria = bd.getTiposInsulinaDosisDiaria();
        InitViewsDiarias();
        listAdapterDiario.notifyDataSetChanged();
    }

    @Override
    public void RecargarListaCorrecciones() {
        listaCorr.clear();
        tipInsCorr.clear();
        tipInsCorr = bd.getTiposInsulinaDosisCorr();
        InitViewsCorr();
        listAdapterCorr.notifyDataSetChanged();
    }
}