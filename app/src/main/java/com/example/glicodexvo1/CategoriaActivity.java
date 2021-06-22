package com.example.glicodexvo1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.Models.CategoriasAlimentos;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ListAdapterCat;

import java.util.ArrayList;

public class CategoriaActivity extends AppCompatActivity {

    ArrayList<CategoriasAlimentos> categorias;
    private RecyclerView recyclerView;
    private ListAdapterCat listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias_alimentos);
        AccesoBD bd = AccesoBD.getInstance(getApplicationContext());
        categorias = bd.getCategorias();
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
        recyclerView = findViewById(R.id.lstCategorias);

        listAdapter = new ListAdapterCat(categorias, this, new ListAdapterCat.OnItemClickListener() {
            @Override
            public void onItemClick(CategoriasAlimentos item) {
                moveToAlimentos(item);
            }
        });
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToAlimentos(CategoriasAlimentos item) {
        Intent intent = new Intent(this, AlimentosActivity.class);
        intent.putExtra("idCategoria", item.getIdCategoria());
        startActivity(intent);
    }
}
