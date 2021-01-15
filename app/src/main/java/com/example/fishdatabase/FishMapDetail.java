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

public class FishMapDetail extends AppCompatActivity {

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

        tvRegionClicked = findViewById(R.id.tvRegionClicked);
        ivRegionReceived = findViewById(R.id.ivRegionReceived);
        tvFishAvailable = findViewById(R.id.tvFishAvailable);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

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