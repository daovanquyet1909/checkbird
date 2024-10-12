package com.example.birdclassifier;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView bannerImageView = findViewById(R.id.bannerImageView);
        Button btnListBirds = findViewById(R.id.btnListBirds);
        Button btnSearchByImage = findViewById(R.id.btnSearchByImage);

        // Sử dụng Glide để bo góc hình ảnh
        Glide.with(this)
                .load(R.drawable.banner) // Thay đổi với tên hình ảnh của bạn
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(16))) // Bo góc 16dp
                .into(bannerImageView);

        btnListBirds.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BirdListActivity.class);
            startActivity(intent);
        });

        btnSearchByImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchByImageActivity.class);
            startActivity(intent);
        });
    }
}
