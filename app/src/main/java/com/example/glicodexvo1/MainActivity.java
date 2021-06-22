package com.example.glicodexvo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.glicodexvo1.Fragments.UsuarioCargar;
import com.example.glicodexvo1.Models.Usuario;
import com.example.glicodexvo1.Utilidades.AccesoBD;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccesoBD bd = AccesoBD.getInstance(getApplicationContext());
        setContentView(R.layout.activity_main);
        Usuario user = bd.getUsuario();

        if(user == null)
        {
            CargarUsuario();
        }
        else {

        }

    }

    private void CargarUsuario() {
        UsuarioCargar cargar = new UsuarioCargar();
        cargar.setCancelable(false);
        cargar.show(getSupportFragmentManager(), "modificar Usuario");
    }

    public void pantalla_usuario(View vista)
    {
        Intent i = new Intent(this, UsuarioPantallaActivity.class);
        startActivity(i);
    }
    public void pantalla_controles(View vista)
    {
        Intent i = new Intent(this, ControlActivity.class);
        startActivity(i);
    }
    public void pantalla_Analisis(View vista)
    {
        //Intent i = new Intent(this, AnalisisActivity.class);
        //startActivity(i);

        Intent i = new Intent(this, AnalisisActivity.class);
        startActivity(i);
    }

    public void pantalla_conteo(View vista)
    {
        Intent i = new Intent(this, CategoriaActivity.class);
        startActivity(i);
    }

    public void pantalla_Reporte(View vista)
    {
        Intent i = new Intent(this, ReportesActivity.class);
        startActivity(i);
    }
}