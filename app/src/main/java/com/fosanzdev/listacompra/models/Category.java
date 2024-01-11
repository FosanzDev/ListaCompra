package com.fosanzdev.listacompra.models;

public class Category implements ItemViewFittable{

    private int id;
    private String nombre;
    private byte[] image;

    //Empty constructor
    public Category() {

    }

    public Category(String nombre, byte[] image) {
        this.nombre = nombre;
        this.image = image;
    }

    public Category(int id, String nombre, byte[] image) {
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

    public byte[] getImage() {
        return image;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
