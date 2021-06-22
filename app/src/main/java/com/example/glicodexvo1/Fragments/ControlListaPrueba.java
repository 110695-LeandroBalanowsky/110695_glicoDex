package com.example.glicodexvo1.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.Models.ControlHorario;
import com.example.glicodexvo1.Models.Horario;
import com.example.glicodexvo1.R;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ListAdapterCtrl;

import java.util.ArrayList;
import java.util.List;

public class ControlListaPrueba extends Fragment implements ControlCargar.CargarControlListener, ControlModificar.ModificarControlListener {


    /*variables*/
    Spinner comboFiltro;
    private List<ControlHorario> elements;
    private RecyclerView recyclerView;
    private ListAdapterCtrl listAdapter;
    AccesoBD bd;
    int idUs;
    String sql= "SELECT idControl, valor, Hora, fecha, h.Horario, Dosis, Comentarios, idUsuario " +
            "FROM Controles c JOIN Horarios h ON c.idHorario= h.idHorarios " +
            "WHERE date(fecha) >= date('now', '-30 days') " +
            "ORDER BY date(fecha) DESC, time(hora) DESC";
    TextView mensaje;

    public ControlListaPrueba() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_controles_lista, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
        idUs = sharedPreferences.getInt("idUsuario", 0);

        bd = AccesoBD.getInstance(getContext());
        elements = getListItemsMensual();
        recyclerView = vista.findViewById(R.id.idListaSemanalControles);
        mensaje= vista.findViewById(R.id.txtMensajeControl);

        /*Carga del spinner*/

        comboFiltro = (Spinner) vista.findViewById(R.id.spnFechasDias);
        ArrayList<String> catCombo= new ArrayList<>();
        catCombo.add("Ultima semana");
        catCombo.add("Ultimos 15 dias");
        catCombo.add("Ultimo mes");

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), R.layout.spinner_item_glicodex, catCombo );
        comboFiltro.setAdapter(adapter);

        /* el selected listener*/
        comboFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0)
                {
                    elements.clear();
                    elements = getListItemsSemanal();

                }
                else
                {
                    if (position == 1)
                    {
                        elements.clear();
                        elements = getListItemsQuincenal();
                    }
                    else
                    {
                        elements.clear();
                        elements = getListItemsMensual();

                    }
                }
                mensaje.setText("");
                listAdapter = new ListAdapterCtrl(elements);
                recyclerView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        InitViews();
        InitValues();

        return vista;
    }


    /*Layout manager*/
    public void InitValues()
    {
        LinearLayoutManager manager;
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
    }

    /*Vistas*/
    private void InitViews()
    {
        listAdapter = new ListAdapterCtrl(elements);
        recyclerView.setAdapter(listAdapter);

    }


    /*Las listas que quiero cambiar*/
    private List<ControlHorario> getListItemsMensual()
    {
        List<ControlHorario> items = bd.getControles("SELECT idControl, valor, Hora, fecha, h.Horario, Dosis, Comentarios, idUsuario " +
                "FROM Controles c JOIN Horarios h ON c.idHorario= h.idHorarios " +
                "WHERE date(fecha) >= date('now', '-30 days') " +
                "ORDER BY date(fecha) DESC, time(hora) DESC;");

        return items;
    }
    private List<ControlHorario> getListItemsQuincenal()
    {
        List<ControlHorario> items = bd.getControles("SELECT idControl, valor, Hora, fecha, h.Horario, Dosis, Comentarios, idUsuario " +
                "FROM Controles c JOIN Horarios h ON c.idHorario= h.idHorarios " +
                "WHERE date(fecha) >= date('now', '-15 days') " +
                "ORDER BY date(fecha) DESC, time(Hora) DESC;");

        return items;
    }
    private List<ControlHorario> getListItemsSemanal()
    {
        List<ControlHorario> items = bd.getControles("SELECT idControl, valor, Hora, fecha, h.Horario, Dosis, Comentarios, idUsuario " +
                "FROM Controles c JOIN Horarios h ON c.idHorario= h.idHorarios " +
                "WHERE date(fecha) >= date('now', '-7 days') " +
                "ORDER BY date(fecha) DESC, time(hora) DESC;");

        return items;
    }

    @Override
    public void RecargarLista() {
        comboFiltro.setSelection(0);
        elements.clear();
        elements = getListItemsSemanal();
        listAdapter = new ListAdapterCtrl(elements);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId())
        {
            case 121:
                ModificarControl(item.getGroupId());
                return true;
            case 122:
                EliminarControl(item.getGroupId());
                return true;
            case 123:
                MostrarCometarios(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void EliminarControl(int position) {
        int idControl = elements.get(position).getIdControl();
        AlertDialog.Builder eliminar = new AlertDialog.Builder(getContext());
        eliminar.setCancelable(false)
                .setMessage("¿Desea eliminar este control?")
                .setTitle("¡Atencion!")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean resultante = bd.elmControl(idControl);
                        if (resultante)
                            Toast.makeText(getContext(), "Control n°: " + resultante, Toast.LENGTH_LONG).show();
                        RecargarLista();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        eliminar.show();
    }

    private void MostrarCometarios(int position) {
        String comentario = elements.get(position).getComentarios();
        Toast.makeText(getContext(), "Comentarios: " + comentario , Toast.LENGTH_LONG).show();
    }

    private void ModificarControl(int position) {
        ControlModificar modificarControl = new ControlModificar();
        modificarControl.setCancelable(false);
        modificarControl.setTargetFragment(ControlListaPrueba.this, 2);
        Bundle bundle = new Bundle();
        bundle.putInt("idCon", elements.get(position).getIdControl());
        modificarControl.setArguments(bundle);
        modificarControl.show(getFragmentManager(),"Modificar control");
    }

    @Override
    public void RecargarListaModificar() {
        RecargarLista();
    }
}