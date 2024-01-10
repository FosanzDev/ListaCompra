package com.fosanzdev.listacompra.models;

public class Category {

    private int id;
    private String nombre;
    private String b64Image;

    //Empty constructor
    public Category() {

    }

    public Category(String nombre, String b64Image) {
        this.nombre = nombre;
        this.b64Image = b64Image;
    }

    public Category(int id, String nombre, String b64Image) {
        this.id = id;
        this.nombre = nombre;
        this.b64Image = b64Image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getB64Image() {
        return b64Image;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
