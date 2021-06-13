package com.example.glicodexvo1.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.glicodexvo1.Models.Control;
import com.example.glicodexvo1.Models.Horario;
import com.example.glicodexvo1.R;
import com.example.glicodexvo1.Utilidades.AccesoBD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ControlModificar extends AppCompatDialogFragment implements View.OnClickListener{

    EditText efecha, ehora, eValor, eComentario;
    private int dia,mes,anio, hora,min;
    Spinner comboHorarios;
    AccesoBD bd;
    int idHorario, idControl;
    Control con;
    String fechaDB, horaDb;
    private ModificarControlListener listener;



    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.cargar_control, null);

        if (getArguments() != null) {
            idControl = getArguments().getInt("idCon", 1);
        }

        builder.setView(vista)
                .setTitle("Modificar Control")
                .setCancelable(false)
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ModificarControl();
                        listener.RecargarListaModificar();
                    }
                });


        bd = AccesoBD.getInstance(getContext());

        ArrayList<Horario> listaCombo = bd.getHorarios();
        eValor = vista.findViewById(R.id.txtValor);
        eComentario = vista.findViewById(R.id.txtComentario);
        ehora = (EditText) vista.findViewById(R.id.txtHora);
        ehora.setFocusable(false);
        ehora.setKeyListener(null);
        efecha = (EditText) vista.findViewById(R.id.txtFechaControl);
        efecha.setFocusable(false);
        efecha.setKeyListener(null);

        comboHorarios = (Spinner) vista.findViewById(R.id.idEstudios);
        ArrayAdapter<Horario> adapter = new ArrayAdapter(getContext(), R.layout.spinner_item_glicodex, listaCombo );
        comboHorarios.setAdapter(adapter);
        comboHorarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idHorario = ((Horario)parent.getSelectedItem()).getIdHorario();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        con = bd.getControl(idControl);
        fechaDB = con.getFecha();
        horaDb = con.getHora();
        eValor.setText(String.valueOf(con.getValor()));
        efecha.setText(con.getFecha());
        ehora.setText(con.getHora());
        eComentario.setText(con.getComentarios());
        comboHorarios.setSelection(con.getHorario()-1);

        efecha.setOnClickListener(this);
        ehora.setOnClickListener(this);

        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    private void ModificarControl() {
        validar();
        if (validar())
        {
            int idCon = idControl;
            int valor = Integer.parseInt(eValor.getText().toString());
            String fecha = fechaDB;
            String hora = horaDb;
            int idHorario = this.idHorario;
            String comentario = eComentario.getText().toString();

            Control conM = new Control(idCon, valor, hora, fecha, idHorario, comentario);
            boolean resultante = bd.modControl(conM);

            if (resultante)
                Toast.makeText(getContext(), "Control n°: " + resultante, Toast.LENGTH_LONG).show();



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
        if (v==ehora)
        {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    ehora.setText(String.format("%02d:%02d", hourOfDay, minute));
                    horaDb = String.format("%02d:%02d", hourOfDay, minute);
                }
            }, hora,min,true);

            timePickerDialog.show();
        }
    }
    public boolean validar()
    {
        try {
            Integer.parseInt(eValor.getText().toString());
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
        if (ehora.getText().toString() == null || ehora.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: la fecha esta vacia",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }






    public interface ModificarControlListener {
        void RecargarListaModificar();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ModificarControlListener) getTargetFragment();
        }
        catch (ClassCastException e)
        {

        }

    }
}
