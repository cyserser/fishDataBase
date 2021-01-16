package com.example.fishdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * En esta pantalla nos mostrará los diferentes regiones del mapa y haciendo click en una de ellas
 * nos llevará al detalle de esa región. Existirá ademas una boton para ir a "fullmap" donde estara
 * el mapa completo y podremos hacer zoom y mover.
 */
public class FishMap extends AppCompatActivity {

    // Declaramos los botones
    private Button btn_balenos, btn_serendia, btn_calpheon, btn_mediah, btn_margoria, btn_fullmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Referenciamos y le asignamos como onClick
        btn_balenos = findViewById(R.id.btn_balenos);
        btn_balenos.setOnClickListener(this::onClick);

        btn_serendia = findViewById(R.id.btn_serendia);
        btn_serendia.setOnClickListener(this::onClick);

        btn_calpheon = findViewById(R.id.btn_calpheon);
        btn_calpheon.setOnClickListener(this::onClick);

        btn_mediah = findViewById(R.id.btn_mediah);
        btn_mediah.setOnClickListener(this::onClick);

        btn_margoria = findViewById(R.id.btn_margoria);
        btn_margoria.setOnClickListener(this::onClick);

        btn_fullmap = findViewById(R.id.btn_fullmap);
        btn_fullmap.setOnClickListener(this::onClick);

    }

    /**
     * Cremos un intent y dependiendo del boton que hayamos pulsado se cargará una region u otra
     * mostrando además un Toast indicando la región pulsada
     * @param v
     */
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {

        Intent intent;
        intent = new Intent (this, FishMapDetail.class);

        switch (v.getId()) {
            case R.id.btn_balenos:
                Toast.makeText(this, "You clicked " + btn_balenos.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra("mapRegionClicked", btn_balenos.getText().toString());
                startActivity(intent);
                break;
            case R.id.btn_serendia:
                Toast.makeText(this, "You clicked " + btn_serendia.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra("mapRegionClicked", btn_serendia.getText().toString());
                startActivity(intent);
                break;
            case R.id.btn_calpheon:
                Toast.makeText(this, "You clicked " + btn_calpheon.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra("mapRegionClicked", btn_calpheon.getText().toString());
                startActivity(intent);
                break;
            case R.id.btn_mediah:
                Toast.makeText(this, "You clicked " + btn_mediah.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra("mapRegionClicked", btn_mediah.getText().toString());
                startActivity(intent);
                break;
            case R.id.btn_margoria:
                Toast.makeText(this, "You clicked " + btn_margoria.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra("mapRegionClicked", btn_margoria.getText().toString());
                startActivity(intent);
                break;
            case R.id.btn_fullmap:
                intent = new Intent (this, FishMapFull.class);
                Toast.makeText(this, "You clicked " + btn_fullmap.getText().toString(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
    }
}