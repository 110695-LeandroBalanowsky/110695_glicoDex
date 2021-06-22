package com.example.glicodexvo1.Utilidades;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.Models.DosisCorrTipo;
import com.example.glicodexvo1.Models.DosisDiariaHorario;
import com.example.glicodexvo1.Models.TipoInsulina;
import com.example.glicodexvo1.R;

import java.util.ArrayList;

public class ListAdapterDosisDiaria extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TIPO_INS = 0;
    private static final int DOSIS_DIARIA = 1;
    ArrayList<Object> data;

    public ListAdapterDosisDiaria(ArrayList<Object> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            //inflate your layout and pass it to view holder
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_padre_cargar_dosis, parent, false);
            return new ListAdapterDosisDiaria.VHTipoIns(view);
        }
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_hijo_cargar_dosis, parent, false);
            return new ListAdapterDosisDiaria.VHDosis(view);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHTipoIns)
        {
            TipoInsulina tipIns = ((TipoInsulina) data.get(position));
            ((VHTipoIns) holder).tipoIns.setText(tipIns.getTipo());
        }
        if (holder instanceof VHDosis)
        {
            DosisDiariaHorario ddh = (DosisDiariaHorario) data.get(position);
            ((VHDosis)holder).horario.setText(ddh.getHorario());
            ((VHDosis)holder).dosis.setText(ddh.getDosis()+"u");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        int tipo = 0;
        if (data.get(position) instanceof TipoInsulina)
        {
            tipo = TIPO_INS;
        }
        if (data.get(position) instanceof DosisDiariaHorario)
        {
            tipo = DOSIS_DIARIA;
        }
        return tipo;
    }

    private Object getItem(int position) {
        return data.get(position);
    }
    class VHTipoIns extends RecyclerView.ViewHolder {
        TextView tipoIns;

        public VHTipoIns(View itemView) {
            super(itemView);
            tipoIns = itemView.findViewById(R.id.txtPadreDosisDiariaCargar);
        }
    }

    class VHDosis extends RecyclerView.ViewHolder{
        TextView horario, dosis;
        ImageButton opciones;

        public VHDosis(View itemView) {
            super(itemView);
            horario = itemView.findViewById(R.id.txtHorarioDosis);
            dosis = itemView.findViewById(R.id.txtDosisHorario);
        }

    }

}
