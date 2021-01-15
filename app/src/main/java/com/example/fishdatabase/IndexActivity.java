package com.example.fishdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class IndexActivity extends AppCompatActivity {

    private Button btn_fishes,btn_map,btn_profile,btn_fishing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_fishes = findViewById(R.id.btn_fishes);
        btn_fishes.setOnClickListener(this::onClick);
        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(this::onClick);
        btn_profile = findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(this::onClick);
        btn_fishing = findViewById(R.id.btn_fishing);
        btn_fishing.setOnClickListener(this::onClick);

    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_fishes:
                Toast.makeText(this, "You clicked " + btn_fishes.getText().toString(), Toast.LENGTH_SHORT).show();
                intent = new Intent (this, FishesActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_map:
                Toast.makeText(this, "You clicked " + btn_map.getText().toString(), Toast.LENGTH_SHORT).show();
                intent = new Intent (this, FishMap.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                Toast.makeText(this, "You clicked " + btn_profile.getText().toString(), Toast.LENGTH_SHORT).show();
                intent = new Intent (this, FishesCaught.class);
                startActivity(intent);
                break;
            case R.id.btn_fishing:
                Toast.makeText(this, "You clicked " + btn_fishing.getText().toString(), Toast.LENGTH_SHORT).show();
                intent = new Intent (this, Fishing.class);
                startActivity(intent);
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}