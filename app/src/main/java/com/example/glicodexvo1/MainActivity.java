package com.example.glicodexvo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.glicodexvo1.Models.Usuario;
import com.example.glicodexvo1.Utilidades.AccesoBD;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccesoBD bd = AccesoBD.getInstance(getApplicationContext());
        Usuario user = bd.getUsuario();

        if(user == null)
        {
            Intent i = new Intent(this, registrarUsuario_activity.class);
            startActivity(i);
        }
        else {
            setContentView(R.layout.activity_main);
        }

    }

    public void pantalla_usuario(View vista)
    {
        Intent i = new Intent(this, usuario_gral.class);
        startActivity(i);
    }
    public void pantalla_controles(View vista)
    {
        Intent i = new Intent(this, ControlActivityPrueba.class);
        startActivity(i);
    }
    public void pantalla_Analisis(View vista)
    {
        Intent i = new Intent(this, AnalisisActivity.class);
        startActivity(i);
    }

    public void pantalla_conteo(View vista)
    {
        Intent i = new Intent(this, categoria_activity.class);
        startActivity(i);
    }

    public void pantalla_Reporte(View vista)
    {
        Intent i = new Intent(this, ReportesActivity.class);
        startActivity(i);
    }
}