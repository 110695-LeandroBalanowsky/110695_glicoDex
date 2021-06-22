package com.example.glicodexvo1.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.glicodexvo1.Models.ControlHorario;
import com.example.glicodexvo1.R;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ListAdapterCtrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ControlListaBuscar extends Fragment implements View.OnClickListener {
    EditText efecha;
    String fechaDB;
    private int dia,mes,anio;
    private List<ControlHorario> elements;
    private RecyclerView recyclerView;
    private ListAdapterCtrl listAdapter;
    TextView mensaje;
    AccesoBD bd;

    public ControlListaBuscar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_controles_buscar, container, false);

        efecha = (EditText) vista.findViewById(R.id.txtFechaControlBuscar);
        efecha.setFocusable(false);
        efecha.setKeyListener(null);
        efecha.setOnClickListener(this);
        recyclerView = vista.findViewById(R.id.recListaBuscar);
        mensaje= vista.findViewById(R.id.txtMensajeBuscar);
        mensaje.setText("Ingrese una fecha para buscar");
        elements = new ArrayList<>();
        bd = AccesoBD.getInstance(getContext());
        LinearLayoutManager manager;
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        return vista;
    }

    public void onClick(View v) {
        if (v == efecha) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    efecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    String fechaC = year + "-" + (month + 1) + "-" + dayOfMonth;
                    SimpleDateFormat fechaFormateada = new SimpleDateFormat("yyyy-MM-dd");
                    String strFecha = "21-01-2017"; //Esta es la fecha que vas obtuviste del picker
                    Date fecha = null;
                    try {
                        fecha = fechaFormateada.parse(fechaC);
                        fechaDB = fechaFormateada.format(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    /*ACA VA LA LLAMADA A LOS METODOS PARA CARGAR LOS COTROLES*/
                    elements.clear();
                    elements = bd.getControlesBuscar(fechaDB);
                    if (elements.isEmpty())
                    {
                        elements.clear();
                        mensaje.setText("No hay controles para el dia " + fechaDB);
                        listAdapter = new ListAdapterCtrl(elements);
                        recyclerView.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }
                    else {
                        mensaje.setText("");
                        listAdapter = new ListAdapterCtrl(elements);
                        recyclerView.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }

                }
            }, anio, mes, dia);

            datePickerDialog.show();
        }
    }
}