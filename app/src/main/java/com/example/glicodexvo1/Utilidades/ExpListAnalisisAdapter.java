package com.example.glicodexvo1.Utilidades;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.glicodexvo1.Models.AnalisisEstudios;
import com.example.glicodexvo1.R;

import java.util.ArrayList;
import java.util.Map;

public class ExpListAnalisisAdapter extends BaseExpandableListAdapter{
    private ArrayList<String> fechas;
    private Map<String, ArrayList<AnalisisEstudios>> estudios;
    private Context context;

    public ExpListAnalisisAdapter(ArrayList<String> fechas, Map<String, ArrayList<AnalisisEstudios>> estudios, Context context) {
        this.fechas = fechas;
        this.estudios = estudios;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return fechas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return estudios.get(fechas.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return fechas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return estudios.get(fechas.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String tituloFecha = (String) getGroup(groupPosition);
            convertView = LayoutInflater.from(context).inflate(R.layout.elemento_fecha_lista_analisis, null);
            TextView fechaVista = convertView.findViewById(R.id.txtFechaAnalisisLista);
            fechaVista.setText(tituloFecha);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        AnalisisEstudios anE = (AnalisisEstudios) getChild(groupPosition, childPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.elemento_analisis_lista, null);
        TextView estudio = convertView.findViewById(R.id.lblEstudio2);
        TextView valor = convertView.findViewById(R.id.lblValorEstudio2);
        estudio.setText(anE.getEstudio());
        valor.setText(anE.getValor() + " " + anE.getMedida());
        ImageButton btnOpciones = convertView.findViewById(R.id.btnModAnal2);
        btnOpciones.setLongClickable(true);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
