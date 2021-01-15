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

public class FishesActivity extends AppCompatActivity {

    List<FetchFishes> fetchFishesList;
    RecyclerView recyclerView;
    FishAdapter fishAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.fishesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchFishesList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("fishes");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

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