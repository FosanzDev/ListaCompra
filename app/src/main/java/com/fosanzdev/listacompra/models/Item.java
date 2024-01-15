package com.fosanzdev.listacompra.models;

import android.graphics.Bitmap;

import com.fosanzdev.listacompra.Utils;

import java.io.Serializable;

public class Item implements ItemViewFittable, Serializable {

    private int id;
    private String nombre;
    private Category category;
    Bitmap image;

    //Empty constructor
    public Item() {

    }

    public Item(String nombre, Category category, Bitmap image) {
        this.nombre = nombre;
        this.category = category;
        this.image = image;
    }

    public Item(int id, String nombre, Category category, Bitmap image) {
        this.id = id;
        this.nombre = nombre;
        this.category = category;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return nombre;
    }

    public Category getCategory() {
        return category;
    }

    public Bitmap getImage() {
        return image;
    }

    @Override
    public String toString() {
        if (category == null)
            return nombre;
        else
            return nombre + " (" + category.getName() + ")";
    }
}
