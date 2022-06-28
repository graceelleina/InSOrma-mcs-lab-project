package com.example.insorma.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insorma.DetailActivity;
import com.example.insorma.FurnitureDetail;
import com.example.insorma.MainActivity;
import com.example.insorma.R;
import com.example.insorma.models.Furniture;

import java.util.Vector;

public class FurnitureAdapter extends RecyclerView.Adapter<FurnitureAdapter.ViewHolder> {

    private Vector<Furniture> listFurniture;
    private Context context;

    public FurnitureAdapter(Context context, Vector<Furniture> listEmployee){
        this.listFurniture = listEmployee;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public FurnitureAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull FurnitureAdapter.ViewHolder holder, int position) {
        // bind the data to your UI
        Furniture e = listFurniture.get(position);
        holder.furName.setText(e.getName());
        holder.furRating.setText(String.valueOf(e.getRating()));
        holder.furPrice.setText(String.valueOf(e.getPrice()));
        holder.furPrice.setText(String.valueOf(e.getPrice()));
        holder.furUsername.setText(String.valueOf(e.getUsername()));

        Glide.with(context)
                .load(e.getPicture())
                .into(holder.furImg);

    }

    @Override
    public int getItemCount() {
        return this.listFurniture.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView furImg;
        private TextView furName, furRating, furPrice, furUsername;
        private CardView cvFurniture;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            cvFurniture = itemView.findViewById(R.id.cvItemFurniture);
            cvFurniture.setOnClickListener(this);
            furImg = itemView.findViewById(R.id.itemImg);
            furName = itemView.findViewById(R.id.itemName);
            furRating = itemView.findViewById(R.id.itemRating);
            furPrice = itemView.findViewById(R.id.itemPrice);
            furUsername = itemView.findViewById(R.id.itemUsername);

        }

        @Override
        public void onClick(View view) {
            Log.v("test", "masuk");
            Intent intent = new Intent(view.getContext(), FurnitureDetail.class);
            furImg.buildDrawingCache();
            Bitmap image = furImg.getDrawingCache();
            Bundle extras = new Bundle();
            extras.putParcelable("imagebitmap", image);
            intent.putExtra( "name",furName.getText().toString());
            intent.putExtra( "rating",furRating.getText().toString());
            intent.putExtra( "price", furPrice.getText().toString());
            intent.putExtra( "username", furUsername.getText().toString());
            intent.putExtras(extras);
            view.getContext().startActivity(intent);
        }
    }

}