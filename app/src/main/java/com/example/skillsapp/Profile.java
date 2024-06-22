package com.example.skillsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView nameTextView;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Récupérer l'ID de l'utilisateur connecté passé via l'Intent
        nameTextView = findViewById(R.id.nameTextView); // Assurez-vous que l'ID correspond à celui défini dans le fichier XML
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        } else {
            // Handle the case where the user is not logged in
            // Redirect to login activity or show an error message
        }

        loadUserProfile();
    }

    private void loadUserProfile() {
        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String userName = document.getString("name");
                            nameTextView.setText(userName);
                        } else {
                            Log.d("Profile", "No such document");
                        }
                    } else {
                        Log.d("Profile", "get failed with ", task.getException());
                    }
                });
    }
    public void CompetenceButton(View view) {
        Intent intent = new Intent(Profile.this, CompetenceActivity.class);
        startActivity(intent);
        finish();
    }
}
