package com.example.glicodexvo1.Utilidades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.Fragments.ControlListaPrueba;
import com.example.glicodexvo1.Fragments.ControlModificar;
import com.example.glicodexvo1.Models.ControlHorario;
import com.example.glicodexvo1.R;

import java.util.List;

public class ListAdapterCtrl extends RecyclerView.Adapter<ListAdapterCtrl.ViewHolder> {
    private List<ControlHorario> mData;


    public ListAdapterCtrl(List<ControlHorario> con) {
        this.mData = con;
    }
    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    @NonNull
    @Override
    public ListAdapterCtrl.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_control, parent, false);
        return new ListAdapterCtrl.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterCtrl.ViewHolder holder, final int position)
    {
        ControlHorario con = mData.get(position);
        holder.valor.setText("" + con.getValor());
        holder.valor.setTextColor(Color.parseColor(con.getColor()));
        holder.fecha.setText(con.getFecha());
        holder.hora.setText(con.getHora());
        holder.horario.setText(con.getHorario());
        holder.dosis.setText("" + con.getDosis());
    }

    public void SetItems(List<ControlHorario> item){mData = item;}

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView valor, fecha, hora, horario, dosis;
        CardView cardControl;
        ImageButton btnOpcCtrl;


        public ViewHolder(View itemView)
        {
            super(itemView);
            valor = itemView.findViewById(R.id.txtValor);
            fecha = itemView.findViewById(R.id.lblfechaVista);
            hora = itemView.findViewById(R.id.lblHoraVista);
            horario = itemView.findViewById(R.id.lblHorarioVista);
            dosis = itemView.findViewById(R.id.lblDosisVista);
            cardControl = itemView.findViewById(R.id.cardControl);
            btnOpcCtrl = itemView.findViewById(R.id.btnOpcionesControl);
            btnOpcCtrl.setOnCreateContextMenuListener(this);
        }


        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.add(this.getBindingAdapterPosition(),121, 0,"Modificar");
            menu.add(this.getBindingAdapterPosition(),122, 1,"Eliminar");
            menu.add(this.getBindingAdapterPosition(),123, 2,"Cometarios");
        }



    }

}
