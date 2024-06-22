package com.example.skillsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create_account_activity extends Activity {
    private static final String TAG = "create_account_activity";

    private ImageView icon;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Animation anim;

    private EditText emailTextView;
    private EditText passwordTextView;
    private EditText nameTextView;
    private EditText phoneTextView;
    private EditText dobTextView;
    private EditText domainTextView;
    private EditText descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.create_account_activity);

        icon = findViewById(R.id.imageView);
        anim = AnimationUtils.loadAnimation(this, R.anim.progressbar_animation);
        icon.startAnimation(anim);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize EditTexts
        emailTextView = findViewById(R.id.editTextEmail);
        passwordTextView = findViewById(R.id.editTextPassword);
        nameTextView = findViewById(R.id.editTextName);
        phoneTextView = findViewById(R.id.editTextPhone);
        dobTextView = findViewById(R.id.editTextDOB);
        domainTextView = findViewById(R.id.editTextDomain);
        descriptionTextView = findViewById(R.id.editTextDescription);
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(create_account_activity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void createUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserToFirestore(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(create_account_activity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToFirestore(FirebaseUser user) {
        if (user != null) {
            String email = emailTextView.getText().toString();
            String password = passwordTextView.getText().toString();
            String name = nameTextView.getText().toString();
            String phone = phoneTextView.getText().toString();
            String dob = dobTextView.getText().toString();
            String domain = domainTextView.getText().toString();
            String description = descriptionTextView.getText().toString();

            Map<String, Object> userData = new HashMap<>();
            userData.put("uid", user.getUid());
            userData.put("email", email);
            userData.put("password", password);
            userData.put("name", name);
            userData.put("phone", phone);
            userData.put("dob", dob);
            userData.put("domain", domain);
            userData.put("description", description);

            db.collection("users").document(user.getUid()).set(userData)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        updateUI(user);
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
        }
    }

    public void onSubmitButtonClick(View view) {
        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        createUserWithEmailAndPassword(email, password);
    }
}
