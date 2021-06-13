package com.example.glicodexvo1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.Models.Alimentos;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ListAdapterAli;

import java.util.ArrayList;

public class alimentos_activity extends AppCompatActivity {

    ArrayList<Alimentos> alimentos;
    private RecyclerView recyclerView;
    private ListAdapterAli listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_alimentos);
        AccesoBD bd = AccesoBD.getInstance(getApplicationContext());
        int idCategoria = (Integer) getIntent().getSerializableExtra("idCategoria");
        alimentos = bd.getAlimentos(idCategoria);
        InitViews();
        InitValues();
    }

    public void InitValues()
    {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }
    private void InitViews()
    {
        recyclerView = findViewById(R.id.lstAlimentos);

        listAdapter = new ListAdapterAli(alimentos);
        recyclerView.setAdapter(listAdapter);
    }
}
