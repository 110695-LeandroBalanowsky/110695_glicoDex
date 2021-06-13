package com.example.glicodexvo1.Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glicodexvo1.R;

import java.util.List;

public class ListAdapterFecha extends RecyclerView.Adapter<ListAdapterFecha.ViewHolder> {
    private List<String> mData;
    private LayoutInflater minflate;
    private Context context;
    final ListAdapterFecha.OnItemClickListener listener;

    public interface OnItemClickListener
    {
        void onItemClick(String item);
    }

    public ListAdapterFecha(List<String> fecha, Context context, ListAdapterFecha.OnItemClickListener listener) {
        this.minflate = LayoutInflater.from(context);
        this.mData = fecha;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ListAdapterFecha.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflate.from(parent.getContext()).inflate(R.layout.elemento_fecha_analisis, parent, false);
        return new ListAdapterFecha.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterFecha.ViewHolder holder, final int position) {
        holder.binData(mData.get(position));


    }

    public void SetItems(List<String> item) {
        mData = item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fechaAnalisis;

        public ViewHolder(View itemView) {
            super(itemView);
            fechaAnalisis = itemView.findViewById(R.id.lblEstudio);
        }

        void binData(final String item) {
            fechaAnalisis.setText(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
