package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredienteAdapter extends RecyclerView.Adapter<IngredienteAdapter.ViewHolder> {

    private List<Ingrediente> ingredientes;

    public IngredienteAdapter(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingrediente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingrediente ingrediente = ingredientes.get(position);
        holder.nombre.setText(ingrediente.nombre);
        holder.cantidad.setText(String.valueOf(ingrediente.cantidad));

        holder.mas.setOnClickListener(v -> {
            ingrediente.cantidad += 1;
            notifyItemChanged(position);
        });

        holder.menos.setOnClickListener(v -> {
            if (ingrediente.cantidad > 0) {
                ingrediente.cantidad -= 1;
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, cantidad;
        ImageButton mas, menos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombreIngrediente);
            cantidad = itemView.findViewById(R.id.tvCantidadIngrediente);
            mas = itemView.findViewById(R.id.btnMasCantidad);
            menos = itemView.findViewById(R.id.btnMenosCantidad);
        }
    }
}
