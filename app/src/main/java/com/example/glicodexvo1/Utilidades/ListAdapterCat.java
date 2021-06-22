package com.example.glicodexvo1.Utilidades;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.Models.CategoriasAlimentos;

import com.example.glicodexvo1.R;

import java.util.List;

public class ListAdapterCat extends RecyclerView.Adapter<ListAdapterCat.ViewHolder> {
    private List<CategoriasAlimentos> mData;
    private LayoutInflater minflate;
    private Context context;
    final ListAdapterCat.OnItemClickListener listener;

    public interface OnItemClickListener
    {
        void onItemClick(CategoriasAlimentos item);
    }

    public ListAdapterCat(List<CategoriasAlimentos> ca, Context context, ListAdapterCat.OnItemClickListener listener) {
        this.minflate = LayoutInflater.from(context);
        this.mData = ca;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ListAdapterCat.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflate.from(parent.getContext()).inflate(R.layout.elemento_categoria, parent, false);
        return new ListAdapterCat.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterCat.ViewHolder holder, final int position) {
        holder.binData(mData.get(position));


    }

    public void SetItems(List<CategoriasAlimentos> item) {
        mData = item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  categoria;
        private ImageView flecha;
        private CardView cardCategoria;

        public ViewHolder(View itemView) {
            super(itemView);
            categoria = itemView.findViewById(R.id.lblEstudio);
            flecha = itemView.findViewById(R.id.flechaCategoria);
            cardCategoria = itemView.findViewById(R.id.cardCategoriaAlimento);
        }

        void binData(final CategoriasAlimentos item) {
            categoria.setText(item.getCategoria());
            cardCategoria.setCardBackgroundColor(Color.parseColor(item.getColor()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
