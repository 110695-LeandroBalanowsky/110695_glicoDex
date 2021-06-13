package com.example.glicodexvo1.Utilidades;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.Models.AnalisisEstudios;
import com.example.glicodexvo1.Models.CategoriasAlimentos;
import com.example.glicodexvo1.R;

import java.util.List;

public class ListAdapterAnalisis extends RecyclerView.Adapter<ListAdapterAnalisis.ViewHolder> {
    private List<AnalisisEstudios> mData;


    public ListAdapterAnalisis(List<AnalisisEstudios> ca) {
        this.mData = ca;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ListAdapterAnalisis.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_analisis, parent, false);
        return new ListAdapterAnalisis.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterAnalisis.ViewHolder holder, final int position) {
        AnalisisEstudios an = mData.get(position);
        holder.estudio.setText(an.getEstudio());
        holder.valor.setText(String.valueOf(an.getValor() ) + an.getMedida());

    }

    public void SetItems(List<AnalisisEstudios> item) {
        mData = item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        private TextView estudio, valor;
        CardView cardAnalisis;
        ImageButton btnModAnali;

        public ViewHolder(View itemView) {
            super(itemView);
            estudio = itemView.findViewById(R.id.lblEstudio);
            valor = itemView.findViewById(R.id.lblValorEstudio);
            cardAnalisis = itemView.findViewById(R.id.cardAnalisis);
            btnModAnali = itemView.findViewById(R.id.btnModAnal);
            btnModAnali.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getBindingAdapterPosition(),221, 0,"Modificar");
            menu.add(this.getBindingAdapterPosition(),222, 1,"Eliminar");
            menu.add(this.getBindingAdapterPosition(),223, 2,"Observaciones");
        }
    }
}
