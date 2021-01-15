package com.example.fishdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view -> goToIndex());
    }

    public void goToIndex(){
        Intent intent = new Intent (this, IndexActivity.class);
        startActivity(intent);
    }
}