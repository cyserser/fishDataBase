package com.example.fishdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta pantalla nos mostrará los peces que hemos capturado con
 * la mini simulación de Fishing, todos los peces que se capturen
 * se mostrarán en esta actividad dentro de un recyclerView.
 *
 * La primera parte del código es similar a la de FishesActivity
 * ahi se comento mas en detalle.
 */

public class FishesCaught extends AppCompatActivity {

    List<FetchFishes> fetchFishesList;
    RecyclerView recyclerView;
    FishAdapterDos fishAdapter;
    DatabaseReference databaseReference;
    TextView tvNoFishCaught;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishes_caught);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // para volver hacia atras

        tvNoFishCaught = findViewById(R.id.tvNoFishCaught);
        tvNoFishCaught.setVisibility(View.INVISIBLE);

        // RecyclerView Firebase
        recyclerView = findViewById(R.id.fishesCaughtRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchFishesList = new ArrayList<>();

        // Esta vez nos interesa el child "fishesCaught"
        databaseReference = FirebaseDatabase.getInstance().getReference("fishesCaught");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            /**
             * En este caso el metodo hara lo mismo que se hizo en FishesActivity
             * salvo que si la lista esta vacia nos mostrará en pantalla un texto
             * mencionando que no se ha atrapado ningún pez
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    FetchFishes data = ds.getValue(FetchFishes.class);
                    fetchFishesList.add(data);
                }

                fishAdapter = new FishAdapterDos(fetchFishesList);
                recyclerView.setAdapter(fishAdapter);

                // Si no hemos capturado ningún pez comprobando la lista de fetchFishes
                // nos mostrará un texto de aviso
                if(fetchFishesList.isEmpty()){
                    tvNoFishCaught.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}