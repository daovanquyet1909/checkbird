package com.example.birdclassifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class SearchByImageActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 71;
    private ImageView imageView;
    private Uri filePath;
    private StorageReference storageReference;
    private Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_image);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Button btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imageView);
        Button btnIdentify = findViewById(R.id.btnIdentify);

        btnTakePhoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        btnUpload.setOnClickListener(v -> uploadImage());

        btnIdentify.setOnClickListener(v -> {
            // Add code to identify the bird
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            imageView.setImageURI(filePath);
            btnUpload.setEnabled(true); // Enable the upload button when an image is selected
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            btnUpload.setEnabled(false); // Disable the upload button to prevent multiple uploads
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("Firebase", "Upload successful");
                            Toast.makeText(SearchByImageActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Firebase", "Upload failed", e);
                            Toast.makeText(SearchByImageActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                            btnUpload.setEnabled(true); // Re-enable the upload button if upload fails
                        }
                    });
        } else {
            Log.e("Firebase", "No file selected");
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
