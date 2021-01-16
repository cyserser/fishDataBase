package com.example.fishdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * En esta pantalla se mostrará el detalle de la región, es decir una imagen de la región y todos los
 * peces que existan en esa región. Si no hay peces registrados en una región saltará un texto en color
 * rojo indicando que no hay peces disponibles.
 */

public class FishMapDetail extends AppCompatActivity {

    // Declaramos los tv, iv ..

    TextView tvRegionClicked,tvFishAvailable;
    String regionClicked;
    ImageView ivRegionReceived;

    List<FetchFishes> fetchFishesList;
    RecyclerView recyclerView;
    FishAdapter fishAdapter;
    DatabaseReference databaseReference;
    CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_map_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Referenciamos
        tvRegionClicked = findViewById(R.id.tvRegionClicked);
        ivRegionReceived = findViewById(R.id.ivRegionReceived);
        tvFishAvailable = findViewById(R.id.tvFishAvailable);

        // Creamos un intent para sacar los datos que nos ha pasado la pantalla FishMap
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // Si no es null el paquete lo desenpaquetamos y sacamos la region
        if(bundle!=null)
        {
            regionClicked = (String) bundle.get("mapRegionClicked");
            tvRegionClicked.setText(regionClicked);
            String image = regionClicked.toLowerCase();
            ivRegionReceived.setImageResource(getResources().getIdentifier(image, "drawable", this.getPackageName()));
        }

        // RecyclerView Firebase
        recyclerView = findViewById(R.id.mapDetailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchFishesList = new ArrayList<>();

        // ahora la ruta es "map" y la región que hemos desenpaquetado del bundle
        databaseReference = FirebaseDatabase.getInstance().getReference("map/"+regionClicked);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    FetchFishes data = ds.getValue(FetchFishes.class);
                    fetchFishesList.add(data);
                }

                fishAdapter = new FishAdapter(fetchFishesList);
                recyclerView.setAdapter(fishAdapter);

                //Si esta vacio la lista, mostramos el texto de no disponible
                if(fetchFishesList.isEmpty()){
                    tvFishAvailable.setText(R.string.fish_no_available);
                    tvFishAvailable.setTextColor(Color.parseColor("#FF0000"));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }
}