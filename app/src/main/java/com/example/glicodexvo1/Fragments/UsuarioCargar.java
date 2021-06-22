package com.example.glicodexvo1.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.glicodexvo1.Models.Usuario;
import com.example.glicodexvo1.R;
import com.example.glicodexvo1.Utilidades.AccesoBD;

public class UsuarioCargar extends AppCompatDialogFragment {
    EditText etnombre, etapellido, etaltura, etpeso;
    Usuario user = null;
    AccesoBD bd;

    public UsuarioCargar() {
        // Required empty public constructor
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.fragment_usuario_cargar, null);

        builder.setView(vista)
                .setCancelable(false)
                .setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        bd = AccesoBD.getInstance(getContext());

        etnombre= vista.findViewById(R.id.txtNombreF);
        etapellido = vista.findViewById(R.id.txtApellidoF);
        etaltura = vista.findViewById(R.id.txtAlturaF);
        etpeso = vista.findViewById(R.id.txtPesoF);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
                if (validar()) {
                    CargarUsuarioF();
                    dismiss();
                }
            }
        });
        return dialog;
    }


    public void CargarUsuarioF()
    {
        String nombre = etnombre.getText().toString();
        String apellido = etapellido.getText().toString();
        double altura = Double.parseDouble(etaltura.getText().toString());
        double peso = Double.parseDouble(etpeso.getText().toString());

        user = new Usuario(nombre,apellido,altura,peso);
        Long resultante = bd.setUsuario(user);

        Toast.makeText(getContext(), "Agregaste al usuario: " + resultante, Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idUsuario", Integer.parseInt(String.valueOf(resultante)));
        editor.commit();
    }



    public boolean validar()
    {
        if(etnombre.getText() == null || etnombre.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: el nombre esta vacio",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etapellido.getText() == null || etapellido.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: el apellido esta vacio",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etaltura.getText().toString() == null || etaltura.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: la altura esta vacia",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(etaltura.getText().toString());
        }
        catch (NumberFormatException E)
        {
            Toast.makeText(getContext(),"Error: la altura no es numerico",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etpeso.getText().toString() == null || etpeso.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: el peso esta vacio",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(etpeso.getText().toString());
        }
        catch (NumberFormatException E)
        {
            Toast.makeText(getContext(),"Error: el peso no es numerico",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}