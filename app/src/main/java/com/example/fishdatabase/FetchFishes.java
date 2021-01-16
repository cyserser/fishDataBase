package com.example.fishdatabase;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Es una clase para que guarda los atributos del pez, asi como su nombre
 * "rarity", la región en la que se puede pescar, tamaño etc.
 *
 * Cabe mencionar que debe implementar serializable, para poder mandarlo como bundle con un intent
 */

public class FetchFishes implements Serializable {
    // Atributos
    String name;
    String rarity;
    String region;
    String size;
    String description;
    String imgName;

    //Constructor vacio
    public FetchFishes() {
    }

    //Getters y Setters muy importante!!!

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
