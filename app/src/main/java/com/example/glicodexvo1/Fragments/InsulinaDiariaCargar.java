package com.example.glicodexvo1.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glicodexvo1.Models.DosisDiaria;
import com.example.glicodexvo1.Models.DosisDiariaHorario;
import com.example.glicodexvo1.Models.Horario;
import com.example.glicodexvo1.Models.TipoInsulina;
import com.example.glicodexvo1.R;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ListAdapterDosisDiaria;
import com.example.glicodexvo1.Utilidades.ListAdapterDosisDiariaElm;

import java.util.ArrayList;

public class InsulinaDiariaCargar extends AppCompatDialogFragment {
    Spinner comboInsulinas, comboHorarios;
    TextView infoInsulina;
    EditText dosis;
    AccesoBD bd;
    int idTipoIns;
    int idHorario, idUsuario;
    Button cargarDosis;
    private CargarDosisDiariaListener listener;
    //ListaDosis
    TextView mensajeDiaria;
    ArrayList<Object> listaDiaria;
    ArrayList<TipoInsulina> tipInsDiaria;
    ArrayList<DosisDiariaHorario> dosisDiaria;
    private RecyclerView recyclerViewDiario;
    private ListAdapterDosisDiariaElm listAdapterDiario;

    public InsulinaDiariaCargar() {
        // Required empty public constructor
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.fragment_insulina_diaria_mod_cargar, null);

        if (getArguments() != null) {
            idUsuario = getArguments().getInt("idUs", 0);
        }

        builder.setView(vista)
                .setTitle("Cargar Insulina diaria")
                .setCancelable(false)
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.RecargarListaDiaria();
                    }
                });

        bd = AccesoBD.getInstance(getContext());
        infoInsulina = vista.findViewById(R.id.txtInformacionInsulina);
        comboInsulinas = vista.findViewById(R.id.cboTiposInsulinaDiario);
        comboHorarios = vista.findViewById(R.id.cboHorariosDiarios);
        cargarDosis = vista.findViewById(R.id.btnCargarDosDiaria);
        dosis = vista.findViewById(R.id.txtDosisDiaria);
        ArrayList<TipoInsulina> tipIns = bd.getTiposInsulina();
        ArrayAdapter<Horario> adapterIns = new ArrayAdapter(getContext(), R.layout.spinner_item_glicodex, tipIns );
        comboInsulinas.setAdapter(adapterIns);
        comboInsulinas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idTipoIns = ((TipoInsulina)parent.getSelectedItem()).getIdTipoInsulina();
                infoInsulina.setText(((TipoInsulina)parent.getSelectedItem()).getInfo());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<Horario> horarios = bd.getHorarios();
        ArrayAdapter<Horario> adapterHorario = new ArrayAdapter(getContext(), R.layout.spinner_item_glicodex, horarios );
        comboHorarios.setAdapter(adapterHorario);
        comboHorarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idHorario = ((Horario)parent.getSelectedItem()).getIdHorario();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cargarDosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cargar();
                tipInsDiaria.clear();
                listaDiaria.clear();
                tipInsDiaria = bd.getTiposInsulinaDosisDiaria();
                InitViewsDiarias();
            }
        });

        //Lista dosis
        tipInsDiaria = bd.getTiposInsulinaDosisDiaria();
        listaDiaria = new ArrayList<>();
        recyclerViewDiario = vista.findViewById(R.id.recDosisDiariaMod);

        InitViewsDiarias();
        InitValuesDiarias();

        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    private void Cargar()
    {
        Validar();
        if(Validar())
        {
            int idTipIns = idTipoIns;
            int dos  = Integer.parseInt(dosis.getText().toString());
            int idHor = idHorario;

            DosisDiaria dosisDiaria = new DosisDiaria(idTipIns,dos,idHor, idUsuario);
            long resultante = bd.setDosisDiaria(dosisDiaria);

            Toast.makeText(getContext(), "Dosis n°: " + resultante, Toast.LENGTH_LONG).show();

            dosis.setText("");
        }
    }

    private boolean Validar() {
        if(dosis.getText() == null || dosis.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: la unidad esta vacia",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(dosis.getText().toString());
        }
        catch (NumberFormatException E)
        {
            Toast.makeText(getContext(),"Error: la unidad no es numerica",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public interface CargarDosisDiariaListener {
        void RecargarListaDiaria();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CargarDosisDiariaListener) getActivity();
        }
        catch (ClassCastException e)
        {

        }

    }
    //Dosis Diarias
    public void InitValuesDiarias()
    {
        LinearLayoutManager manager;
        manager = new LinearLayoutManager(getContext());
        recyclerViewDiario.setLayoutManager(manager);
    }
    private void InitViewsDiarias()
    {
        if (!tipInsDiaria.isEmpty())
        {
            for(int i = 0; i < tipInsDiaria.size(); i++)
            {
                listaDiaria.add(tipInsDiaria.get(i));
                dosisDiaria = bd.getDosisDiaria(tipInsDiaria.get(i).getIdTipoInsulina());
                for (int j = 0; j < dosisDiaria.size(); j++)
                {
                    listaDiaria.add(dosisDiaria.get(j));
                }

            }
            listAdapterDiario = new ListAdapterDosisDiariaElm(listaDiaria, getContext());
            recyclerViewDiario.setAdapter(listAdapterDiario);
        }

    }


}