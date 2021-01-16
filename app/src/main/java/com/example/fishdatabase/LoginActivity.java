package com.example.fishdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *  Esta es la activity Login, es decir la pantalla inicial de la app. Solo tendrá un botón para acceder a la siguiente
 *  pantalla, la de Index
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Declaramos el boton login
     */
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try
        {
            this.getSupportActionBar().hide(); // en la pantalla login no se mostrará el toolbar
        }
        catch (NullPointerException e){}

        btn_login = findViewById(R.id.btn_login); // referenciamos el boton con findviewbyid
        btn_login.setOnClickListener(view -> goToIndex());
    }

    // metodo para pasar de pantalla a la de index mediante un intent
    public void goToIndex(){
        Intent intent = new Intent (this, IndexActivity.class);
        startActivity(intent);
    }
}