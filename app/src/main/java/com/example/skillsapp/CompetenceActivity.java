package com.example.skillsapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CompetenceActivity extends Activity {
    DatabaseReference Mref;
    ListView mylistview;
    ArrayList<String> myArrayList = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competence);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(CompetenceActivity.this, android.R.layout.simple_list_item_1,myArrayList);
        mylistview = (ListView) findViewById(R.id.listview1);
        mylistview.setAdapter(myArrayAdapter);
        Mref = FirebaseDatabase.getInstance().getReference();
        Mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot Snapshot, @Nullable String previousChildName) {

                String value = Snapshot.getValue(String.class);
                myArrayAdapter.add(value);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
