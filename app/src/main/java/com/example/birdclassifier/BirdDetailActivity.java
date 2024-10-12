package com.example.birdclassifier;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class BirdDetailActivity extends AppCompatActivity {

    private TextView commonNameTextView;
    private TextView scientificNameTextView;
    private TextView orderTextView;
    private TextView familyTextView;
    private TextView genousTextView;
    private ImageView avatarImageView;
    private ImageView img2ImageView;
    private ImageView img3ImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_detail);

        avatarImageView = findViewById(R.id.avatarImageView);
        commonNameTextView = findViewById(R.id.commonNameTextView);
        scientificNameTextView = findViewById(R.id.scientificNameTextView);
        orderTextView = findViewById(R.id.orderTextView);
        familyTextView = findViewById(R.id.familyTextView);
        genousTextView = findViewById(R.id.genousTextView);
        img2ImageView = findViewById(R.id.img2ImageView);
        img3ImageView = findViewById(R.id.img3ImageView);

        Bird bird = getIntent().getParcelableExtra("bird");

        if (bird != null) {
            commonNameTextView.setText("Common name: " + bird.common_name);
            scientificNameTextView.setText("Scientific name: " + bird.scientific_name);
            orderTextView.setText("Order: " + bird.order);
            familyTextView.setText("Family: " + bird.family);
            genousTextView.setText("Genous: " + bird.genous);

            Picasso.get().load(bird.img1).into(avatarImageView);
            Picasso.get().load(bird.img2).into(img2ImageView);
            Picasso.get().load(bird.img3).into(img3ImageView);
        }
    }
}
