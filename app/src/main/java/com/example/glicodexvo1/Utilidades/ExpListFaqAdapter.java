package com.example.glicodexvo1.Utilidades;

import android.content.Context;
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

public class ExpListFaqAdapter extends BaseExpandableListAdapter{
    private ArrayList<String> pregunta;
    private Map<String, String> respuestas;
    private Context context;

    public ExpListFaqAdapter(ArrayList<String> pregunta, Map<String, String> respuestas, Context context) {
        this.pregunta = pregunta;
        this.respuestas = respuestas;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return pregunta.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return pregunta.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return respuestas.get(pregunta.get(groupPosition));
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
        String pregunta = (String) getGroup(groupPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.elemento_fecha_lista_analisis, null);
        TextView fechaVista = convertView.findViewById(R.id.txtFechaAnalisisLista);
        fechaVista.setText(pregunta);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String resp = (String) getChild(groupPosition, childPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.elemento_faq, null);
        TextView txtFaq = convertView.findViewById(R.id.txtFaq);
        txtFaq.setText(resp);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
