package com.example.birdclassifier;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BirdAdapter extends RecyclerView.Adapter<BirdAdapter.BirdViewHolder> {

    private List<Bird> birdList;
    private List<Bird> birdListFull; // Danh sách đầy đủ để tìm kiếm
    private Context context;

    public BirdAdapter(List<Bird> birdList) {
        this.birdList = birdList;
        this.birdListFull = new ArrayList<>(birdList); // Sao chép danh sách ban đầu
    }

    @NonNull
    @Override
    public BirdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.detail_item, parent, false);
        return new BirdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BirdViewHolder holder, int position) {
        Bird bird = birdList.get(position);
        holder.commonNameTextView.setText(bird.common_name);
        holder.scientificNameTextView.setText(bird.scientific_name);
        Picasso.get().load(bird.img1).into(holder.avatarImageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BirdDetailActivity.class);
            intent.putExtra("bird", bird);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return birdList.size();
    }

    public boolean filter(String text) {
        birdList.clear();
        if (text.isEmpty()) {
            birdList.addAll(birdListFull);
        } else {
            text = text.toLowerCase();
            for (Bird bird : birdListFull) {
                if (bird.common_name != null && bird.common_name.toLowerCase().contains(text)) {
                    birdList.add(bird);
                }
            }
        }
        notifyDataSetChanged();
        return birdList.isEmpty();
    }

    public static class BirdViewHolder extends RecyclerView.ViewHolder {

        ImageView avatarImageView;
        TextView commonNameTextView;
        TextView scientificNameTextView;

        public BirdViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            commonNameTextView = itemView.findViewById(R.id.commonNameTextView);
            scientificNameTextView = itemView.findViewById(R.id.scientificNameTextView);
        }
    }
}
