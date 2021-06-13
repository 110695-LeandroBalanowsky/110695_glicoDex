package com.example.glicodexvo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glicodexvo1.Fragments.AnalisisModificar;
import com.example.glicodexvo1.Models.AnalisisEstudios;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ListAdapterAnalisis;

import java.util.List;

public class ListaEstudiosActivity extends AppCompatActivity {

    private String fecha;
    private List<AnalisisEstudios> elements;
    private RecyclerView recyclerView;
    private ListAdapterAnalisis listAdapter;
    private AccesoBD db;
    TextView fechaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estudios);
        fecha = (String)getIntent().getSerializableExtra("fecha");
        db = AccesoBD.getInstance(getApplicationContext());
        recyclerView = findViewById(R.id.recEstudiosAnalisis);
        fechaText = findViewById(R.id.tstFechaAnalisis);
        fechaText.setText(fecha);
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
        listAdapter = new ListAdapterAnalisis(elements);
        recyclerView.setAdapter(listAdapter);
    }

    private List<AnalisisEstudios> getListItems()
    {
        List<AnalisisEstudios> items = db.getAnalisisEstudios(fecha);
        return items;
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case 221:
                ModificarAnalisis(item.getGroupId());
                return true;
            case 222:
                EliminarAnalisis(item.getGroupId());
                return true;
            case 223:
                MostrarCometarios(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);
        }



    }

    private void EliminarAnalisis(int position) {
        int idAnalisis = elements.get(position).getIdAnalisis();
        android.app.AlertDialog.Builder eliminar = new android.app.AlertDialog.Builder(this);
        eliminar.setCancelable(false)
                .setMessage("¿Desea eliminar este analisis?")
                .setTitle("¡Atencion!")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean resultante = db.elmAnalisis(idAnalisis);
                        if (resultante)
                            Toast.makeText(getApplicationContext(), "Analisis n°: " + resultante, Toast.LENGTH_LONG).show();
                        //RecargarLista();
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
        String comentario = elements.get(position).getObservaciones();
        Toast.makeText(getApplicationContext(), comentario , Toast.LENGTH_LONG).show();
    }

    private void ModificarAnalisis(int position) {
        /*ControlModificar modificarControl = new ControlModificar();
        modificarControl.setCancelable(false);
        modificarControl.setTargetFragment(ControlListaPrueba.this, 2);
        Bundle bundle = new Bundle();
        bundle.putInt("idCon", elements.get(position).getIdControl());
        modificarControl.setArguments(bundle);
        modificarControl.show(getFragmentManager(),"Modificar control");*/

        AnalisisModificar modificAn = new AnalisisModificar();
        Bundle bundle = new Bundle();
        bundle.putInt("idAn", elements.get(position).getIdAnalisis());
        modificAn.setArguments(bundle);
        modificAn.show(getSupportFragmentManager(), "modificar Analisis");
    }
}