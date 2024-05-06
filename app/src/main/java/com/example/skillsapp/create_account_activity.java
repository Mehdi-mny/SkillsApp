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

public class create_account_activity extends Activity {
    private static final String TAG = "create_account_activity";

    ImageView icon;
    private FirebaseAuth mAuth;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.create_account_activity);
        icon = findViewById(R.id.imageView);
        anim = AnimationUtils.loadAnimation(create_account_activity.this, R.anim.progressbar_animation);
        icon.startAnimation(anim);

        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, navigate to your main activity or do something else
        }
    }
    private void updateUI(FirebaseUser user) {
        // Code to update UI after authentication
        Intent intent = new Intent(create_account_activity.this,HomeActivity.class);
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
                            // Call a method to update UI
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(create_account_activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void onSubmitButtonClick(View view) {
        // Récupérer les valeurs de TextViews
        EditText emailTextView = findViewById(R.id.editTextEmail);
        EditText passwordTextView = findViewById(R.id.editTextPassword);

        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        // Appeler la méthode createUserWithEmailAndPassword avec les valeurs récupérées
        createUserWithEmailAndPassword(email, password);
    }
}
