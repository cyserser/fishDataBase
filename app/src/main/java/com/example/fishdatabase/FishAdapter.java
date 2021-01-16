package com.example.fishdatabase;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 *  Esta clase es una adapter que lo utilizará nuestro recyclerView
 *
 */
public class FishAdapter extends RecyclerView.Adapter{

    //Creamos una lista
    List<FetchFishes> fetchFishesList;

    //Constructor
    public FishAdapter(List<FetchFishes> fetchFishesList) {
        this.fetchFishesList = fetchFishesList;

    }

    /**
     * Este método se encargará de "inflar" el layout example_item
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    /**
     * En este método nos mostrará los datos en las diferentes posiciones del recyclerView
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Creamos el viewHodler
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;

        //Obtenemos la posición y le pasamos el nombre y rarity
        FetchFishes fetchFishes = fetchFishesList.get(position);
        viewHolderClass.name.setText(fetchFishes.getName());
        viewHolderClass.rarity.setText(fetchFishes.getRarity());
        String fishName = fetchFishes.getName();
        String fishRarity = fetchFishes.getRarity();

        getRarity(viewHolderClass, fishRarity);
        getImgName(viewHolderClass, fishName);

    }

    /**
     * Metodo que cambia de color al texto "rarity" dependiendo del rarity, es decir si es "common"
     * el color de rarity saldra verde y por el contrario si es "rare" el color del rarity
     * saldra en color azul.
     * @param viewHolderClass
     * @param fishRarity
     */
    private void getRarity(ViewHolderClass viewHolderClass, String fishRarity) {
        if(fishRarity.equals("common")){
            viewHolderClass.rarity.setTextColor(Color.parseColor("#00FF00"));
        } else {
            viewHolderClass.rarity.setTextColor(Color.parseColor("#0000FF"));
        }
    }

    /**
     * Metodo que nos hara de setImageResource dependiendo del pez que le pasemos, o sea
     * si le pasamos "mudskipper" nos hara un set de su imagen localizado en R.Drawable
     * @param viewHolderClass
     * @param fishName
     */
    private void getImgName(ViewHolderClass viewHolderClass, String fishName) {
        if(fishName.equals("Mudskipper")){
            viewHolderClass.imgName.setImageResource(R.drawable.mudskipper);
        } else if(fishName.equals("Lenok")){
            viewHolderClass.imgName.setImageResource(R.drawable.lenok);
        } else {
            viewHolderClass.imgName.setImageResource(R.drawable.nibbler);
        }
    }

    /**
     * Devuelve el tamaño de la lista
     * @return
     */
    @Override
    public int getItemCount() {
        return fetchFishesList.size();
    }

    /**
     * Aqui es donde declaramos los textViews y ImageViews, donde posteriormente
     * los encuentra mediante findviewById. También dispondra de un onclick para saber
     * en que posición de la lista hemos hecho click, de tal manera que nos lleve al detalle
     * de ese pez clickeado con sus caracterisitcas pasandole un "fishItem" que contendra
     * el nombre, region, rarity etc.
     */
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView name, rarity;
        ImageView imgName;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rarity = itemView.findViewById(R.id.rarity);
            imgName = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {

                    Intent intent = new Intent(itemView.getContext(), FishesDetailActivity.class);

                    // get position
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        FetchFishes clickedDataItem = fetchFishesList.get(pos);
                        Toast.makeText(itemView.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("fishItem", clickedDataItem);
                        itemView.getContext().startActivity(intent);
                    }

                }
            });

        }

    }



}
