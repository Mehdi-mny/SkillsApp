package com.example.skillsapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Démarrer l'activité SplashScreen
        Intent intent = new Intent(MainActivity.this, SplashScreen.class);
        startActivity(intent);
        // Assurez-vous de terminer MainActivity pour qu'elle ne soit pas empilée dans la pile d'activités
        finish();
    }
}
