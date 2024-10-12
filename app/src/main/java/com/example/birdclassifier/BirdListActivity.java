package com.example.birdclassifier;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BirdListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BirdAdapter birdAdapter;
    private List<Bird> birdList;
    private List<Bird> birdListFull;
    private DatabaseReference databaseReference;
    private Button searchButton;
    private TextView noResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        birdList = new ArrayList<>();
        birdListFull = new ArrayList<>();
        birdAdapter = new BirdAdapter(birdList);
        recyclerView.setAdapter(birdAdapter);

        searchButton = findViewById(R.id.searchButton);
        noResultsTextView = findViewById(R.id.noResultsTextView);

        databaseReference = FirebaseDatabase.getInstance().getReference("imagebird");
        fetchBirdData();

        searchButton.setOnClickListener(v -> showSearchDialog());
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tìm kiếm");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.search_dialog, (ViewGroup) findViewById(android.R.id.content), false);
        final EditText input = viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
            String query = input.getText().toString().trim().toLowerCase();
            Log.d("BirdListActivity", "Search query: " + query);
            boolean noResults = filterBirdList(query);
            if (noResults) {
                noResultsTextView.setVisibility(View.VISIBLE);
            } else {
                noResultsTextView.setVisibility(View.GONE);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void fetchBirdData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                birdList.clear();
                birdListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Bird bird = dataSnapshot.getValue(Bird.class);
                    if (bird != null) {
                        birdList.add(bird);
                        birdListFull.add(bird);
                    }
                }
                birdAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BirdListActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean filterBirdList(String query) {
        birdList.clear();
        for (Bird bird : birdListFull) {
            if (bird.common_name != null && bird.common_name.toLowerCase().contains(query)) {
                birdList.add(bird);
            }
        }
        birdAdapter.notifyDataSetChanged();
        return birdList.isEmpty();
    }
}
