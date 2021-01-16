package com.example.fishdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Esta pantalla nos mostrará los detalles del pez cuando hacemos clic
 * en uno de los peces que se presenta en el recyclerViewen la pantalla
 * de FishesActivity.
 */

public class FishesDetailActivity extends AppCompatActivity {

    /*Declaramos los TextViews, imageview y el fishItem para desenpaquetarlo
    ya que contiene todos los atributos de un pez. Recordemos que se lo
    pasamos como un intent EXTRA en la clase de fishAdapter método
    onclick de ViewholderClass*/

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

        // Referenciamos..
        nameReceived = findViewById(R.id.tvFishNameReceived);
        rarityReceived = findViewById(R.id.tvFishRarityReceived);
        sizeReceived = findViewById(R.id.tvFishSizeReceived);
        regionReceived = findViewById(R.id.tvFishRegionReceived);
        imageReceived = findViewById(R.id.ivReceived);
        descriptionReceived = findViewById(R.id.tvFishDescriptionReceived);

        // Creamos un intent para sacar los "extras" del paquete
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // Si no es null el paquete asignamos al fishItem el valor del bundle
        // y actualizamos los valores de los textviews
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

    /**
     * Método que nos hace un set de la imagen del pez, si le pasamos
     * mudskipper como pez pues nos hara un set de ese imagen para ese pez
     */
    private void getImage() {
        String image = nameReceived.getText().toString().toLowerCase();
        imageReceived.setImageResource(getResources().getIdentifier(image, "drawable", this.getPackageName()));
    }

    /**
     * Metodo que pasandonos el rarity del pez nos pondra en un color u otro
     * el texto Rarity
     */

    private void getRarity() {
        if(rarityReceived.getText().toString().equals("common")){
            rarityReceived.setTextColor(Color.parseColor("#00FF00"));
        } else {
            rarityReceived.setTextColor(Color.parseColor("#0000FF"));
        }
    }
}