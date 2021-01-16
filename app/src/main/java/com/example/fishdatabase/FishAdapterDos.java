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

import java.util.List;

/**
 * Para resumir, es igual al la clase FishAdapter salvo que le pasamos el
 * nombre y Size en vez de nombre y rarity, el resto es casi igual.
 */

public class FishAdapterDos extends RecyclerView.Adapter{

    List<FetchFishes> fetchFishesList;

    public FishAdapterDos(List<FetchFishes> fetchFishesList) {
        this.fetchFishesList = fetchFishesList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;

        FetchFishes fetchFishes = fetchFishesList.get(position);
        viewHolderClass.name.setText(fetchFishes.getName());
        viewHolderClass.size.setText(fetchFishes.getSize());
        String fishName = fetchFishes.getName();
        getImgName(viewHolderClass, fishName);

    }


    private void getImgName(ViewHolderClass viewHolderClass, String fishName) {
        if(fishName.equals("Mudskipper")){
            viewHolderClass.imgName.setImageResource(R.drawable.mudskipper);
        } else if(fishName.equals("Lenok")){
            viewHolderClass.imgName.setImageResource(R.drawable.lenok);
        } else {
            viewHolderClass.imgName.setImageResource(R.drawable.nibbler);
        }
    }

    @Override
    public int getItemCount() {
        return fetchFishesList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView name, rarity, size;
        ImageView imgName;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            size = itemView.findViewById(R.id.rarity);
            imgName = itemView.findViewById(R.id.imageView);


        }


    }

}
