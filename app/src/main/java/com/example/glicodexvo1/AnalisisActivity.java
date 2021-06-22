package com.example.glicodexvo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.glicodexvo1.Fragments.AnalisisCargar;
import com.example.glicodexvo1.Fragments.AnalisisModificar;
import com.example.glicodexvo1.Models.AnalisisEstudios;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ExpListAnalisisAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalisisActivity extends AppCompatActivity implements AnalisisCargar.CargarAnalisisListener, AnalisisModificar.ModificarAnalisisListener {
    private ExpandableListView listaAnalisis;
    private ExpListAnalisisAdapter adapter;
    private ArrayList<String> fechas;
    private Map<String, ArrayList<AnalisisEstudios>> estudios;
    private Spinner cboAniosAnalisis;
    AccesoBD bd;
    String fecha;

    ArrayList<String> anioCombo;
    ArrayAdapter<String> adapterCombo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analisis_prueba);
        bd = AccesoBD.getInstance(this);
        cboAniosAnalisis = findViewById(R.id.spnListaAniosPrueba);
        anioCombo= bd.getAnioAnalisis();
        adapterCombo = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_glicodex, anioCombo );
        cboAniosAnalisis.setAdapter(adapterCombo);

        /* el selected listener*/
        cboAniosAnalisis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fecha = parent.getSelectedItem().toString();
                fechas = bd.getFechasAnalisis(fecha);
                CargarDatos();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listaAnalisis = findViewById(R.id.listAnalisis);
        registerForContextMenu(listaAnalisis);
        fechas = bd.getFechasAnalisis(fecha);
        estudios = new HashMap<>();
        CargarDatos();
    }

    private void CargarDatos()
    {
        for (int i = 0; i < fechas.size(); i ++)
        {
            ArrayList<AnalisisEstudios> analisisEstudios = bd.getAnalisisEstudios(fechas.get(i));
            estudios.put(fechas.get(i), analisisEstudios);
        }
        adapter = new ExpListAnalisisAdapter(fechas, estudios, this);
        listaAnalisis.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void Cargar_analisis(View vista)
    {
        AnalisisCargar cargarAnalisis = new AnalisisCargar();
        cargarAnalisis.show(getSupportFragmentManager(),"Cargar analisis");
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type =
                ExpandableListView.getPackedPositionType(info.packedPosition);

        int group =
                ExpandableListView.getPackedPositionGroup(info.packedPosition);

        int child =
                ExpandableListView.getPackedPositionChild(info.packedPosition);

        // Only create a context menu for child items
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD)
        {
            menu.add(0, 200, 0, "Modificar");
            menu.add(0, 201, 0, "Eliminar");
            menu.add(0, 202, 0, "Informacion");
        }
    }
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuItem.getMenuInfo();

        int groupPos = 0, childPos = 0;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD)
        {
            groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
        }


        switch (menuItem.getItemId())
        {
            case 200:
                ModificarAnalisis(groupPos,childPos);
                return true;

            case 201:
                EliminarAnalisis(groupPos, childPos);
                return true;

            case 202:
                MostrarCometarios(groupPos, childPos);
                return true;

            default:
                return super.onContextItemSelected(menuItem);
        }
    }
    private void EliminarAnalisis(int groupposition, int child) {
        AnalisisEstudios analisis = (AnalisisEstudios) adapter.getChild(groupposition, child);
        android.app.AlertDialog.Builder eliminar = new android.app.AlertDialog.Builder(this);
        eliminar.setCancelable(false)
                .setMessage("¿Desea eliminar este analisis?")
                .setTitle("¡Atencion!")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean resultante = bd.elmAnalisis(analisis.getIdAnalisis());
                        if (resultante)
                            Toast.makeText(getApplicationContext(), "Analisis n°: " + resultante, Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                        fechas.clear();
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

    private void MostrarCometarios(int groupposition, int child) {
        AnalisisEstudios analisis = (AnalisisEstudios) adapter.getChild(groupposition, child);
        String comentario = analisis.getObservaciones();
        Toast.makeText(getApplicationContext(), comentario , Toast.LENGTH_LONG).show();
    }

    private void ModificarAnalisis(int groupposition, int child) {
        AnalisisModificar modificAn = new AnalisisModificar();
        Bundle bundle = new Bundle();
        AnalisisEstudios analisis = (AnalisisEstudios) adapter.getChild(groupposition, child);
        bundle.putInt("idAn", analisis.getIdAnalisis());
        modificAn.setArguments(bundle);
        modificAn.show(getSupportFragmentManager(), "modificar Analisis");
    }

    @Override
    public void RecargarLista() {
        anioCombo.clear();
        anioCombo= bd.getAnioAnalisis();
        adapterCombo = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_glicodex, anioCombo );
        if (!anioCombo.isEmpty())
        {
            cboAniosAnalisis.setAdapter(adapterCombo);
            fecha = cboAniosAnalisis.getSelectedItem().toString();
            fechas = bd.getFechasAnalisis(fecha);
        }
        CargarDatos();
    }
}