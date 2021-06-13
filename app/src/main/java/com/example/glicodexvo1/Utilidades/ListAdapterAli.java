package com.example.glicodexvo1.Utilidades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.Models.Alimentos;
import com.example.glicodexvo1.R;

import java.util.List;

public class ListAdapterAli extends RecyclerView.Adapter<ListAdapterAli.ViewHolder> {
    private List<Alimentos> mData;


    public ListAdapterAli(List<Alimentos> ali) {
        this.mData = ali;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ListAdapterAli.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_alimento, parent, false);
        return new ListAdapterAli.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterAli.ViewHolder holder, final int position) {
        Alimentos ali = mData.get(position);
        holder.alimento.setText(ali.getAlimento());
        holder.porcion.setText(ali.getMedida());
        holder.ig.setText(ali.getIg());
        //holder.ig.setTextColor(Color.parseColor(ali.getColor()));
        holder.car.setText(String.valueOf(ali.getCarbo()));
        holder.cal.setText(String.valueOf(ali.getCalo()));


    }

    public void SetItems(List<Alimentos> item) {
        mData = item;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView alimento, porcion, ig, car, cal;

        public ViewHolder(View itemView) {
            super(itemView);
            alimento = itemView.findViewById(R.id.lblAlimento);
            porcion = itemView.findViewById(R.id.lblPorcion);
            ig = itemView.findViewById(R.id.lblIg);
            car = itemView.findViewById(R.id.lblCarbo);
            cal = itemView.findViewById(R.id.lblCalo);
        }

    }
}