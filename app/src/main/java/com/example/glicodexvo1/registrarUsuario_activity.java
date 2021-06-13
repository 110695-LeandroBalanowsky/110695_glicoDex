package com.example.glicodexvo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.glicodexvo1.Models.Usuario;
import com.example.glicodexvo1.Utilidades.AccesoBD;

public class registrarUsuario_activity extends AppCompatActivity {
    EditText etnombre, etapellido, etaltura, etpeso;
    Usuario user = null;
    AccesoBD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario_activity);
        bd = AccesoBD.getInstance(getApplicationContext());

        etnombre= findViewById(R.id.txtNombre);
        etapellido = findViewById(R.id.txtApellido);
        etaltura = findViewById(R.id.txtAltura);
        etpeso = findViewById(R.id.txtPeso);
    }
    @Override
    public void onBackPressed() {

    }


    public void CargarUsuario(View view)
    {
        String nombre = etnombre.getText().toString();
        String apellido = etapellido.getText().toString();
        double altura = Double.parseDouble(etaltura.getText().toString());
        double peso = Double.parseDouble(etpeso.getText().toString());

        user = new Usuario(nombre,apellido,altura,peso);
        Long resultante = bd.setUsuario(user);

        Toast.makeText(getApplicationContext(), "Agregaste al usuario: " + resultante, Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idUsuario", Integer.parseInt(String.valueOf(resultante)));
        editor.commit();
        finish();

    }
}