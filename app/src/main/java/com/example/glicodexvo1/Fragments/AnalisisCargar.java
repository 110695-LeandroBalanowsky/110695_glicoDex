package com.example.glicodexvo1.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.glicodexvo1.Models.Analisis;
import com.example.glicodexvo1.Models.Control;
import com.example.glicodexvo1.Models.Estudios;
import com.example.glicodexvo1.Models.Horario;
import com.example.glicodexvo1.R;
import com.example.glicodexvo1.Utilidades.AccesoBD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AnalisisCargar extends AppCompatDialogFragment implements View.OnClickListener {

    EditText efecha, eValor;
    private int dia,mes,anio;
    Spinner comboEstudios;
    AccesoBD bd;
    int idEstudio, idUsuario;
    String fechaDB;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.fragment_cargar_analisis, null);

        if (getArguments() != null) {
            idUsuario = getArguments().getInt("idUs", 0);
        }

        bd = AccesoBD.getInstance(getContext());

        builder.setView(vista)
                .setTitle("Cargar Analisis")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Cargar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        ArrayList<Estudios> listaCombo = bd.getEstudios();

        eValor = (EditText) vista.findViewById(R.id.txtValorAnalisis);
        efecha = (EditText) vista.findViewById(R.id.txtFechaAnalisis);
        efecha.setFocusable(false);
        efecha.setKeyListener(null);

        comboEstudios = (Spinner) vista.findViewById(R.id.idEstudios);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getContext(), R.layout.spinner_item_glicodex, listaCombo );

        comboEstudios.setAdapter(adapter);
        comboEstudios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idEstudio = ((Estudios)parent.getSelectedItem()).getIdEstudioi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        efecha.setOnClickListener(this);


        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarAnalisis();
            }
        });



        return dialog;
    }
    public boolean validar()
    {
        if (efecha.getText() == null || efecha.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: la fecha esta vacia",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(eValor.getText().toString());
        }
        catch (NumberFormatException E)
        {
            Toast.makeText(getContext(),"Error: el valor no es numerico",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(eValor.getText() == null || eValor.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: el valor esta vacio",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (efecha.getText() == null || efecha.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: la fecha esta vacia",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void CargarAnalisis() {
        validar();
        if (validar())
        {
            String fecha = fechaDB;
            double valor = Double.parseDouble(eValor.getText().toString());
            int idEstudio = this.idEstudio;


            Analisis an = new Analisis( fecha, valor, idEstudio, idUsuario);
            long resultante = bd.setAnalisis(an);

            Toast.makeText(getContext(), "Analisis n°: " + resultante, Toast.LENGTH_LONG).show();

            eValor.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        if (v==efecha)
        {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes= c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    efecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    String fechaC = year+"-"+(month+1)+"-"+dayOfMonth;
                    SimpleDateFormat fechaFormateada = new SimpleDateFormat("yyyy-MM-dd");
                    String strFecha = "21-01-2017"; //Esta es la fecha que vas obtuviste del picker
                    Date fecha = null;
                    try {
                        fecha = fechaFormateada.parse(fechaC);
                        fechaDB = fechaFormateada.format(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, anio, mes, dia);

            datePickerDialog.show();
        }

    }
}