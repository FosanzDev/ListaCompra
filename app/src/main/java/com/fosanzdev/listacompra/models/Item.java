package com.fosanzdev.listacompra.models;

public class Item implements ItemViewFittable{

    private int id;
    private String nombre;
    private Category category;
    private byte[] image;

    //Empty constructor
    public Item() {

    }

    public Item(String nombre, Category category, byte[] image) {
        this.nombre = nombre;
        this.category = category;
        this.image = image;
    }

    public Item(int id, String nombre, Category category, byte[] image) {
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

    public byte[] getImage() {
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
