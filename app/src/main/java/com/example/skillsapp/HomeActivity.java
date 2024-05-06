package com.example.skillsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
    }

    public void ProfileButton(View view) {
        Intent intent = new Intent(HomeActivity.this,Profile.class);
        startActivity(intent);
        finish();
    }
}
