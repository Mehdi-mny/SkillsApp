package com.example.skillsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class Profile extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
    }

    public void CompetenceButton(View view) {
        Intent intent = new Intent(Profile.this,CompetenceActivity.class);
        startActivity(intent);
        finish();
    }

    public void envoyermessage(View view) {
        Intent intent = new Intent(Profile.this, MessagingActivity.class);
        intent.putExtra("RECIPIENT_ID", "NHfqSusuSIZ4cn2Eg4FZqWpKUqd2");
        intent.putExtra("RECIPIENT_NAME", "test");
        startActivity(intent);

    }
}
