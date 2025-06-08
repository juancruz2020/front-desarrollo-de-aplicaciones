package com.example.myapplication;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.PromoViewHolder> {

    private List<Integer> imagenes;
    private Context context;

    public PromoAdapter(Context context, List<Integer> imagenes) {
        this.context = context;
        this.imagenes = imagenes;
    }

    @NonNull
    @Override
    public PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Crear CardView contenedor
        CardView cardView = new CardView(context);
        cardView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        cardView.setRadius(24); // ajustá el radio que te guste
        cardView.setCardElevation(0);
        cardView.setUseCompatPadding(true);
        cardView.setPreventCornerOverlap(true);

        // Crear ImageView dentro del CardView
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Agregar ImageView al CardView
        cardView.addView(imageView);

        return new PromoViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoViewHolder holder, int position) {
        holder.imageView.setImageResource(imagenes.get(position));
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    static class PromoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PromoViewHolder(@NonNull CardView itemView) {
            super(itemView);
            imageView = (ImageView) itemView.getChildAt(0);
        }
    }
}
