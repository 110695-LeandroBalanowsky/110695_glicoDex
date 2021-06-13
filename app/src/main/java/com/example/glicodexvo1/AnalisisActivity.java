package com.example.glicodexvo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.glicodexvo1.Fragments.AnalisisCargar;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ListAdapterFecha;

import java.util.ArrayList;
import java.util.List;

public class AnalisisActivity extends AppCompatActivity {

    private List<String> elements;
    private RecyclerView recyclerView;
    private ListAdapterFecha listAdapter;
    private AccesoBD bd;
    private Spinner cboAniosAnalisis;
    String fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_analisis);

        bd = AccesoBD.getInstance(this);

        recyclerView = findViewById(R.id.idListaAnalisisAnual);
        cboAniosAnalisis = findViewById(R.id.spnAniosAnalisis);

        ArrayList<String> anioCombo= bd.getAnioAnalisis();

        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_glicodex, anioCombo );
        cboAniosAnalisis.setAdapter(adapter);

        /* el selected listener*/
        cboAniosAnalisis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fecha = parent.getSelectedItem().toString();
                elements.clear();
                elements = getListItems();
                listAdapter = new ListAdapterFecha(elements, getApplicationContext(), new ListAdapterFecha.OnItemClickListener() {
                    @Override
                    public void onItemClick(String item) {
                        MostrarAnalisis(item);
                    }
                });
                recyclerView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        InitViews();
        InitValues();
    }
    public void InitValues()
    {
        LinearLayoutManager manager;
        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
    }
    private void InitViews()
    {
        elements = getListItems();
        listAdapter = new ListAdapterFecha(elements, getApplicationContext(), new ListAdapterFecha.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                MostrarAnalisis(item);
            }
        });
        recyclerView.setAdapter(listAdapter);
    }

    private void MostrarAnalisis(String item) {
        /*MostrarAnalisis mostrarAnalisis = new MostrarAnalisis();
        Bundle bundle = new Bundle();
        bundle.putString("fecha", item);
        mostrarAnalisis.setArguments(bundle);
        mostrarAnalisis.show(getSupportFragmentManager(), "Mostrar Analisis");*/

        Intent intent = new Intent(this, ListaEstudiosActivity.class);
        intent.putExtra("fecha", item);
        startActivity(intent);
    }

    private List<String> getListItems()
    {
        List<String> items = bd.getFechasAnalisis(fecha);

        return items;
    }

    public void Cargar_analisis(View vista)
    {
        AnalisisCargar cargarAnalisis = new AnalisisCargar();
        cargarAnalisis.show(getSupportFragmentManager(),"Cargar analisis");


    }
}