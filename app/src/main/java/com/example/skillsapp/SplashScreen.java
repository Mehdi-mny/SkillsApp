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
        logo = findViewById(R.id.imageView);
        bar = findViewById(R.id.progressBar);

        logo.startAnimation(anim);

        // Après une certaine durée ou lorsqu'une condition est remplie (par exemple, lorsque votre animation de chargement est terminée),
        // vous pouvez démarrer l'activité HomeActivity
        // Ici, nous utilisons un Handler pour retarder l'exécution pendant quelques secondes (simulant un chargement)
        new android.os.Handler().postDelayed(
                () -> {
                    // Créer un Intent pour démarrer l'activité HomeActivity
                    Intent intent = new Intent(SplashScreen.this, create_account_activity.class);
                    // Démarrer l'activité HomeActivity
                    startActivity(intent);
                    // Terminer l'activité SplashScreen pour qu'elle ne soit pas empilée dans la pile d'activités
                    finish();
                },
                3000 // Spécifiez la durée d'attente en millisecondes (ici, 3 secondes)
        );
    }
}
