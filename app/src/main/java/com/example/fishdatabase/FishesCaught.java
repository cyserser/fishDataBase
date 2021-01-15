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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvNoFishCaught = findViewById(R.id.tvNoFishCaught);
        tvNoFishCaught.setVisibility(View.INVISIBLE);

        // RecyclerView Firebase
        recyclerView = findViewById(R.id.fishesCaughtRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchFishesList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("fishesCaught");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    FetchFishes data = ds.getValue(FetchFishes.class);
                    fetchFishesList.add(data);
                }

                fishAdapter = new FishAdapterDos(fetchFishesList);
                recyclerView.setAdapter(fishAdapter);

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