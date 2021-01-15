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

public class FishAdapter extends RecyclerView.Adapter{

    List<FetchFishes> fetchFishesList;

    public FishAdapter(List<FetchFishes> fetchFishesList) {
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
//        String mDrawableName = "mudskipper";
//        int resID = context.getResources().getIdentifier(mDrawableName , "drawable", context.getPackageName());
//        String fishName = fetchFishes.getName();
//        int fishNameDos = context.getResources().getIdentifier(fishName, "drawable", context.getPackageName());
        FetchFishes fetchFishes = fetchFishesList.get(position);
        viewHolderClass.name.setText(fetchFishes.getName());
        viewHolderClass.rarity.setText(fetchFishes.getRarity());
        String fishName = fetchFishes.getName();
        String fishRarity = fetchFishes.getRarity();

        getRarity(viewHolderClass, fishRarity);
        getImgName(viewHolderClass, fishName);

    }

    private void getRarity(ViewHolderClass viewHolderClass, String fishRarity) {
        if(fishRarity.equals("common")){
            viewHolderClass.rarity.setTextColor(Color.parseColor("#00FF00"));
        } else {
            viewHolderClass.rarity.setTextColor(Color.parseColor("#0000FF"));
        }
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
