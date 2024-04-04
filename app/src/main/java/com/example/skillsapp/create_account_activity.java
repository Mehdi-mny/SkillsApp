package com.example.skillsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class create_account_activity extends Activity {
    ImageView icon;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);
        icon = findViewById(R.id.imageView);
        anim = AnimationUtils.loadAnimation(create_account_activity.this, R.anim.progressbar_animation);

        icon.startAnimation(anim);
    }
}
