package com.example.fishdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FishesDetailActivity extends AppCompatActivity {

    TextView nameReceived = null;
    TextView rarityReceived = null;
    TextView sizeReceived = null;
    TextView regionReceived = null;
    TextView descriptionReceived = null;
    ImageView imageReceived = null;
    FetchFishes fishItem = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishes_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameReceived = findViewById(R.id.tvFishNameReceived);
        rarityReceived = findViewById(R.id.tvFishRarityReceived);
        sizeReceived = findViewById(R.id.tvFishSizeReceived);
        regionReceived = findViewById(R.id.tvFishRegionReceived);
        imageReceived = findViewById(R.id.ivReceived);
        descriptionReceived = findViewById(R.id.tvFishDescriptionReceived);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
        {
            fishItem = (FetchFishes) bundle.get("fishItem");
            nameReceived.setText(fishItem.getName());
            rarityReceived.setText(fishItem.getRarity());
            getRarity();
            sizeReceived.setText(fishItem.getSize());
            regionReceived.setText(fishItem.getRegion());
            getImage();
            descriptionReceived.setText(fishItem.getDescription());
        }


    }

    private void getImage() {
        String image = nameReceived.getText().toString().toLowerCase();
        imageReceived.setImageResource(getResources().getIdentifier(image, "drawable", this.getPackageName()));
    }

    private void getRarity() {
        if(rarityReceived.getText().toString().equals("common")){
            rarityReceived.setTextColor(Color.parseColor("#00FF00"));
        } else {
            rarityReceived.setTextColor(Color.parseColor("#0000FF"));
        }
    }
}