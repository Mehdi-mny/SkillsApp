package com.example.skillsapp;

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
    private TextView domainTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView descriptionTextView;
    private String userId;
    private String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expert_profile);

        nameTextView = findViewById(R.id.nameTextView);
        domainTextView = findViewById(R.id.domainTextView); // Added domainTextView
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        db = FirebaseFirestore.getInstance();

        // Retrieve the UID of the user passed via the Intent
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
                            String userDomain = document.getString("domain"); // Retrieve domain
                            String userEmail = document.getString("email");
                            String userPhone = document.getString("phone");
                            String userdescription = document.getString("description");

                            nameTextView.setText(userName);
                            domainTextView.setText(userDomain); // Set domain
                            emailTextView.setText(userEmail);
                            phoneTextView.setText(userPhone);
                            descriptionTextView.setText(userdescription);
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
