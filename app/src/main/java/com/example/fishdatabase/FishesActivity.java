package com.example.fishdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta pantalla nos mostrará todos los peces disponibles en la base de datos
 * de Firebase en un recyclerView, mostrandonos un icono del pez, el nombre y el "rarity"
 */
public class FishesActivity extends AppCompatActivity {

    // Declaramos las listas, recycler, adapters y la referencia a la base de datos.
    List<FetchFishes> fetchFishesList;
    RecyclerView recyclerView;
    FishAdapter fishAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Para ir a la pantalla anterior

        // Localizamos el recyclerView y le hacemos un set del layout
        recyclerView = findViewById(R.id.fishesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Creamos un arrayList para almacenar los peces
        fetchFishesList = new ArrayList<>();

        //Referenciamos a la base de datos de firebase en este caso el child "fishes"
        databaseReference = FirebaseDatabase.getInstance().getReference("fishes");

        //Añadimos un ValueListener para hacer el fetchado de los datos
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            /**
             * En este método nos añadirá a la arrayList que hemos creado
             * anteriormente todos los peces mediante un for los va recorriendo
             * También se creará el adapter fishAdapter y lo usara
             * el recyclerView.
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    FetchFishes data = ds.getValue(FetchFishes.class);
                    fetchFishesList.add(data);
                }

                fishAdapter = new FishAdapter(fetchFishesList);
                recyclerView.setAdapter(fishAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}