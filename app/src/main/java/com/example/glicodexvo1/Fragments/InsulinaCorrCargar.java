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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glicodexvo1.Models.DosisCorr;
import com.example.glicodexvo1.Models.DosisCorrTipo;
import com.example.glicodexvo1.Models.Horario;
import com.example.glicodexvo1.Models.TipoInsulina;
import com.example.glicodexvo1.R;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ListAdapterDosisCorr;
import com.example.glicodexvo1.Utilidades.ListAdapterDosisCorrElm;

import java.util.ArrayList;


public class InsulinaCorrCargar extends AppCompatDialogFragment {
    AccesoBD bd;
    Spinner comboInsulinas;
    TextView infoInsulina;
    EditText desde, hasta, dosis;
    int idTipoIns;
    int idUsuario;
    Button cargarDosis;
    CheckBox finalValor;
    private CargarDosisCorrListener listener;
    //Correctiva lista
    ArrayList<Object> listaCorr;
    ArrayList<TipoInsulina> tipInsCorr;
    ArrayList<DosisCorrTipo> dosisCorr;
    private RecyclerView recyclerViewCorr;
    private ListAdapterDosisCorrElm listAdapterCorr;

    public InsulinaCorrCargar() {
        // Required empty public constructor
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.fragment_insulina_corr_cargar, null);

        if (getArguments() != null) {
            idUsuario = getArguments().getInt("idUs", 0);
        }

        builder.setView(vista)
                .setTitle("Cargar correcciones")
                .setCancelable(false)
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.RecargarListaCorrecciones();
                    }
                });

        bd = AccesoBD.getInstance(getContext());
        infoInsulina = vista.findViewById(R.id.txtInformacionInsulinaCorr);
        comboInsulinas = vista.findViewById(R.id.cboTiposInsulinaCorr);
        cargarDosis = vista.findViewById(R.id.btnCargarDosCorr);
        desde = vista.findViewById(R.id.txtDesde);
        hasta = vista.findViewById(R.id.txtHasta);
        dosis = vista.findViewById(R.id.txtDosisCorr);
        finalValor = vista.findViewById(R.id.chkUltimoValor);
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
        finalValor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    hasta.setVisibility(View.INVISIBLE);
                    desde.setHint("Mas de:");
                }
                else
                {
                    hasta.setVisibility(View.VISIBLE);
                    desde.setHint("Desde:");
                }
            }
        });

        cargarDosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cargar();
                tipInsCorr.clear();
                listaCorr.clear();
                tipInsCorr = bd.getTiposInsulinaDosisDiaria();
                InitViewsCorr();
            }
        });

        //Lista correcciones
        tipInsCorr = bd.getTiposInsulinaDosisCorr();
        listaCorr = new ArrayList<>();
        recyclerViewCorr = vista.findViewById(R.id.recDosisCorrElm);

        InitViewsCorr();
        InitValuesCorr();

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
            int desde = Integer.parseInt(this.desde.getText().toString());
            int hasta = 0;
            if (finalValor.isChecked())
            {
                hasta = 999;
            }
            else
            {
                hasta = Integer.parseInt(this.hasta.getText().toString());
            }

            int dos = Integer.parseInt(dosis.getText().toString());


            DosisCorr dosisCorr = new DosisCorr(desde, hasta, dos, idTipIns, idUsuario);
            long resultante = bd.setDosisCorrectiva(dosisCorr);

            this.desde.setText("");
            this.hasta.setText("");
            dosis.setText("");
        }
    }

    private boolean Validar() {
        if(desde.getText() == null || desde.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: La escala debe comenzar con un valor",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(desde.getText().toString());
        }
        catch (NumberFormatException E)
        {
            Toast.makeText(getContext(),"Error: la escala debe ser numerica",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!finalValor.isChecked())
        {
            if(hasta.getText() == null || hasta.getText().toString().trim().equals(""))
            {
                Toast.makeText(getContext(),"Error: La escala debe terminar con un valor (de ser el ultimo indicar en 'Ultimo Valor')",Toast.LENGTH_SHORT).show();
                return false;
            }
            try {
                Integer.parseInt(hasta.getText().toString());
            }
            catch (NumberFormatException E)
            {
                Toast.makeText(getContext(),"Error: la escala debe ser numerica",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(dosis.getText() == null || dosis.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"Error: La dosis no puede estar vacia",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(dosis.getText().toString());
        }
        catch (NumberFormatException E)
        {
            Toast.makeText(getContext(),"Error: la dosis debe ser numerica",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    //Lista Correcciones
    public void InitValuesCorr()
    {
        LinearLayoutManager manager;
        manager = new LinearLayoutManager(getContext());
        recyclerViewCorr.setLayoutManager(manager);
    }


    private void InitViewsCorr()
    {
        if (!tipInsCorr.isEmpty())
        {
            for(int i = 0; i < tipInsCorr.size(); i++)
            {
                listaCorr.add(tipInsCorr.get(i));
                dosisCorr = bd.getDosisCorr(tipInsCorr.get(i).getIdTipoInsulina());
                for (int j = 0; j < dosisCorr.size(); j++)
                {
                    listaCorr.add(dosisCorr.get(j));
                }

            }
            listAdapterCorr = new ListAdapterDosisCorrElm(listaCorr, getContext());
            recyclerViewCorr.setAdapter(listAdapterCorr);
        }
    }

    public interface CargarDosisCorrListener {
        void RecargarListaCorrecciones();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CargarDosisCorrListener) getActivity();
        }
        catch (ClassCastException e)
        {

        }

    }


}