package com.example.fishdatabase;

import java.io.Serializable;

/**
 * Clase para la pantalla de FishesMap, en la que nos mostrará
 * en el recyclerView el nombre del pez y el "rarity"
 */
public class FetchFishesMap implements Serializable {

    //Atributos
    String name;
    String rarity;

    //Constructor vacio
    public FetchFishesMap() {
    }

    //Getters y Setters
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
}
