package com.example.cc14.smartcarrent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginType extends AppCompatActivity {

    Button btnOwner, btnUser;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);

        preferences = new Preferences(this);

        btnOwner = findViewById(R.id.btnOwner);
        btnUser = findViewById(R.id.btnUser);

        btnOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginType.this, LoginActivity.class);
                preferences.setUserType("Owner");
                startActivity(intent);
            }
        });

        btnOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginType.this, LoginActivity.class);
                preferences.setUserType("User");
                startActivity(intent);
            }
        });
    }
}
