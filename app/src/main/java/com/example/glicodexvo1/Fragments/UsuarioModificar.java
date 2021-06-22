package com.example.glicodexvo1.Fragments;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glicodexvo1.Models.Control;
import com.example.glicodexvo1.Models.Horario;
import com.example.glicodexvo1.Models.Usuario;
import com.example.glicodexvo1.R;
import com.example.glicodexvo1.Utilidades.AccesoBD;

import java.util.ArrayList;

public class UsuarioModificar extends AppCompatDialogFragment {
    TextView nombre, apellido, altura, peso;
    int idUsuario;
    AccesoBD bd;
    Usuario us;
    private ModificarUsuarioListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.fragment_usuario_modificar, null);

        if (getArguments() != null) {
            idUsuario = getArguments().getInt("idUs", 1);
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
        int idUs = sharedPreferences.getInt("idUsuario", 0);



        builder.setView(vista)
                .setTitle("Modificar usuario")
                .setCancelable(false)
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ModificarUsuario();
                        listener.RecargarDatos();
                    }
                });

        bd = AccesoBD.getInstance(getContext());

        nombre = vista.findViewById(R.id.txtNombreMod);
        apellido = vista.findViewById(R.id.txtApellidoMod);
        altura = vista.findViewById(R.id.txtAlturaMod);
        peso = vista.findViewById(R.id.txtPesoMod);

        us = bd.getUsuarioxId(idUs);

        nombre.setText(us.getNombre());
        apellido.setText(us.getApellido());
        altura.setText(String.valueOf(us.getAltura()));
        peso.setText(String.valueOf(us.getPeso()));

        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }
    private void ModificarUsuario() {
        validar();
        if (validar())
        {

            String nombreMod = nombre.getText().toString();
            String apellidoMod = apellido.getText().toString();
            Double alturaMod = Double.parseDouble(altura.getText().toString());
            Double pesoMod = Double.parseDouble(peso.getText().toString());

            Usuario user = new Usuario(idUsuario, nombreMod, apellidoMod, alturaMod, pesoMod);
            boolean resultante = bd.modUsuario(user);

            if (resultante == true)
                Toast.makeText(getContext(), "Usuarioi modificado " + resultante, Toast.LENGTH_LONG).show();

        }
    }
    public boolean validar()
    {
        if(nombre.getText() == null || nombre.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: el nombre esta vacio",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (apellido.getText() == null || apellido.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: el apellido esta vacio",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (altura.getText().toString() == null || altura.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: la altura esta vacia",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(altura.getText().toString());
        }
        catch (NumberFormatException E)
        {
            Toast.makeText(getContext(),"Error: la altura no es numerico",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (peso.getText().toString() == null || peso.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: el peso esta vacio",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(peso.getText().toString());
        }
        catch (NumberFormatException E)
        {
            Toast.makeText(getContext(),"Error: el peso no es numerico",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    public interface ModificarUsuarioListener {
        void RecargarDatos();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ModificarUsuarioListener) getActivity();
        }
        catch (ClassCastException e)
        {

        }

    }
}