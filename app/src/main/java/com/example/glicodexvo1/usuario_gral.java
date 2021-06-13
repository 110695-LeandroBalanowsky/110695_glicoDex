package com.example.glicodexvo1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.glicodexvo1.Models.Usuario;
import com.example.glicodexvo1.Utilidades.AccesoBD;

public class usuario_gral extends Activity {
    TextView nombreCompleto, altura, peso;
    AccesoBD bd;
    Usuario user = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_pantalla);
        nombreCompleto = findViewById(R.id.lblNombreCompleto);
        altura= findViewById(R.id.lblAltura);
        peso = findViewById(R.id.lblPeso);

        bd = AccesoBD.getInstance(getApplicationContext());
        cargarDatos();

    }
    public void cargarDatos()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        int idUs = sharedPreferences.getInt("idUsuario", 0);

        user = bd.getUsuarioxId(idUs);

        nombreCompleto.setText(user.getNombre() + " " + user.getApellido());
        altura.setText(String.valueOf(user.getAltura())+"cm");
        peso.setText(String.valueOf(user.getPeso())+"kg");
    }


}
