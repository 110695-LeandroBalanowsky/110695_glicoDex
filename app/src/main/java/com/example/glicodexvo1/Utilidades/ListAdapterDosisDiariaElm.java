package com.example.glicodexvo1.Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.Models.DosisDiariaHorario;
import com.example.glicodexvo1.Models.TipoInsulina;
import com.example.glicodexvo1.R;

import java.util.ArrayList;

public class ListAdapterDosisDiariaElm extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TIPO_INS = 0;
    private static final int DOSIS_DIARIA = 1;
    ArrayList<Object> data;
    Context context;

    public ListAdapterDosisDiariaElm(ArrayList<Object> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            //inflate your layout and pass it to view holder
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_padre_cargar_dosis, parent, false);
            return new ListAdapterDosisDiariaElm.VHTipoIns(view);
        }
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_hijo_elm_dosis, parent, false);
            return new ListAdapterDosisDiariaElm.VHDosis(view);
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
            ((VHDosis)holder).eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccesoBD bd = AccesoBD.getInstance(context);
                    DosisDiariaHorario dd = (DosisDiariaHorario) data.get(position);
                    bd.elmDosisDiaria(dd.getIdDosisDiaria());
                    data.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());

                }
            });
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

    class VHDosis extends RecyclerView.ViewHolder {
        TextView horario, dosis;
        ImageButton eliminar;

        public VHDosis(View itemView) {
            super(itemView);
            horario = itemView.findViewById(R.id.txtHorarioDosisElm);
            dosis = itemView.findViewById(R.id.txtDosisHorarioElm);
            eliminar = itemView.findViewById(R.id.elmDosisDiaria);

        }
    }

}

