package com.example.skillsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    Animation anim;
    ImageView logo;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splashscreen);
        anim = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.progressbar_animation);
        bar = findViewById(R.id.progressBar);



        new android.os.Handler().postDelayed(
                () -> {
                    Intent intent = new Intent(SplashScreen.this, create_account_activity.class);
                    startActivity(intent);
                    finish();
                },
                3000 //(ici, 3 secondes)
        );
    }
}
