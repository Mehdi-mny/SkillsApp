package com.example.skillsapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class CompetenceActivity extends Activity {
    private LinearLayout cardContainer;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Competence";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competence);

        cardContainer = findViewById(R.id.cardContainer); // Initialisation du cardContainer

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddReclamationDialog();
            }
        });

        loadReclamations(); // Chargement des réclamations
    }

    public void ajouter(View view) {
        showAddReclamationDialog();
    }

    private void showAddReclamationDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.addcompetence);

        // Make the dialog full screen width
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        EditText editTextTitle = dialog.findViewById(R.id.editTextTitle);
        EditText editTextDescription = dialog.findViewById(R.id.editTextDescription);
        Button buttonSubmit = dialog.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(CompetenceActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> Comptence = new HashMap<>();
                Comptence.put("titre", title); // Utiliser "title" au lieu de "titre"
                Comptence.put("description", description);

                Log.d(TAG, Comptence.toString());

                db.collection("Competence").add(Comptence) // Utiliser la collection "reclamations"
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(CompetenceActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                addReclamationCard(title, description); // Ajouter la nouvelle réclamation à la vue
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CompetenceActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        dialog.show();
    }

    private void loadReclamations() {
        db.collection("Competence")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("titre");
                            String description = document.getString("description");
                            addReclamationCard(title, description);
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    private void addReclamationCard(String title, String description) {
        View cardView = getLayoutInflater().inflate(R.layout.competence_card, cardContainer, false);

        TextView titleTextView = cardView.findViewById(R.id.competenceTitle);
        TextView descriptionTextView = cardView.findViewById(R.id.competenceDesc);

        titleTextView.setText(title);
        descriptionTextView.setText(description);

        cardContainer.addView(cardView);
    }

    public void retour(View view) {
        Intent intent = new Intent(CompetenceActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
