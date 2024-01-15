package com.fosanzdev.listacompra.models;

import android.graphics.Bitmap;

public class Category implements ItemViewFittable{

    private int id;
    private String nombre;
    private Bitmap image;

    //Empty constructor
    public Category() {

    }

    public Category(String nombre, Bitmap image) {
        this.nombre = nombre;
        this.image = image;
    }

    public Category(int id, String nombre, Bitmap image) {
        this.id = id;
        this.nombre = nombre;
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

    public Bitmap getImage() {
        return image;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
