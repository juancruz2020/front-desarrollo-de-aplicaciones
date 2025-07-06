package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dto.RecetaDTO;

import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder> {

    private List<RecetaDTO> listaRecetas;
    private Context context;

    public RecetaAdapter(List<RecetaDTO> listaRecetas, Context context) {
        this.listaRecetas = listaRecetas;
        this.context = context;
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_receta_foto, parent, false);
        return new RecetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecetaViewHolder holder, int position) {
        RecetaDTO receta = listaRecetas.get(position);
        holder.tvNombreReceta.setText(receta.getNombre());

        // no hay imagen en el DTO
        holder.ivReceta.setImageResource(R.drawable.ic_default);
    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }

    public class RecetaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivReceta;
        TextView tvNombreReceta;

        public RecetaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivReceta = itemView.findViewById(R.id.imageReceta);
            tvNombreReceta = itemView.findViewById(R.id.textTitulo);
        }
    }
}
