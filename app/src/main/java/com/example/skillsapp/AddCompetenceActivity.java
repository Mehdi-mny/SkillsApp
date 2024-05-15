package com.example.skillsapp;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCompetenceActivity extends Activity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "CompetenceActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void addCompetenceButtonClick(View view) {
        Map<String, Object> category = new HashMap<>();
        category.put("2", "data engineer");

        Log.d(TAG, category.toString());

        db.collection("Category").add(category)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddCompetenceActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(AddCompetenceActivity.this,"Failed",Toast.LENGTH_SHORT).show();


                    }
                });
    }

}