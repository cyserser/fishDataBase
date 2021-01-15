package com.example.fishdatabase;

import java.io.Serializable;

public class FetchFishesMap implements Serializable {

    String name;
    String rarity;

    public FetchFishesMap() {
    }

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
