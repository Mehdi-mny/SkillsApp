package com.example.skillsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
public class ExpertProfile extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private String userId;
    private String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expert_profile);

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        db = FirebaseFirestore.getInstance();

        // Récupérer l'UID de l'utilisateur passé via l'Intent
        userId = getIntent().getStringExtra("USER_UID");
        if (userId != null) {
            loadUserProfile();
        }
    }

    private void loadUserProfile() {
        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            userName = document.getString("name");
                            String userEmail = document.getString("email");
                            String userPhone = document.getString("phone");


                            nameTextView.setText(userName);
                            emailTextView.setText(userEmail);
                            phoneTextView.setText(userPhone);
                        } else {
                            Log.d("ExpertProfile", "No such document");
                        }
                    } else {
                        Log.d("ExpertProfile", "get failed with ", task.getException());
                    }
                });
    }

    public void envoyermessage(View view) {
        Intent intent = new Intent(ExpertProfile.this, MessagingActivity.class);
        intent.putExtra("RECIPIENT_ID", userId);
        intent.putExtra("RECIPIENT_NAME", userName);
        startActivity(intent);
    }

}

