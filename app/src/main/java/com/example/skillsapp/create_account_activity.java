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

    ImageView icon;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.create_account_activity);

        icon = findViewById(R.id.imageView);
        anim = AnimationUtils.loadAnimation(create_account_activity.this, R.anim.progressbar_animation);
        icon.startAnimation(anim);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
/*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }
*/
    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(create_account_activity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void createUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(create_account_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserToFirestore(user);
                            Intent intent = new Intent(create_account_activity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(create_account_activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToFirestore(FirebaseUser user) {
        if (user != null) {

            EditText passwordTextView = findViewById(R.id.editTextPassword);
            EditText nameTextView = findViewById(R.id.editTextName);
            EditText phoneTextView = findViewById(R.id.editTextPhone);
            EditText dobTextView = findViewById(R.id.editTextDOB);


            String password = passwordTextView.getText().toString();
            String name = nameTextView.getText().toString();
            String phone = phoneTextView.getText().toString();
            String dob = dobTextView.getText().toString();

            // Create a new user with a first, last name and email
            Map<String, Object> userData = new HashMap<>();
            userData.put("uid", user.getUid());
            userData.put("email", user.getEmail());
            userData.put("password", password);
            userData.put("name", name);
            userData.put("phone", phone);
            userData.put("dob", dob);


            // Add a new document with a generated ID
            db.collection("users").document(user.getUid()).set(userData)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        updateUI(user);
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
        }
    }

    public void onSubmitButtonClick(View view) {
        // Retrieve the values from EditTexts
        EditText emailTextView = findViewById(R.id.editTextEmail);
        EditText passwordTextView = findViewById(R.id.editTextPassword);

        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        // Call the method to create user with email and password
        createUserWithEmailAndPassword(email, password);
    }
}
