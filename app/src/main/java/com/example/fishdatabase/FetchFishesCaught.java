package com.example.fishdatabase;

import java.io.Serializable;

/**
 * Clase que se utilizará para la pantalla de peces capturados
 * en la que nos mostrará en un RecyclerView el nombre del pez capturado
 * junto con su tamaño.
 */

public class FetchFishesCaught implements Serializable {

    //Atributos
    String name;
    String size;

    //Constructor
    public FetchFishesCaught(String name, String size) {
        this.name = name;
        this.size = size;
    }

    //Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
