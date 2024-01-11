package com.fosanzdev.listacompra.models;

public class Item implements ItemViewFittable{

    private int id;
    private String nombre;
    private Category category;
    private String b64Image;

    //Empty constructor
    public Item() {

    }

    public Item(String nombre, Category category, String b64Image) {
        this.nombre = nombre;
        this.category = category;
        this.b64Image = b64Image;
    }

    public Item(int id, String nombre, Category category, String b64Image) {
        this.id = id;
        this.nombre = nombre;
        this.category = category;
        this.b64Image = b64Image;
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

    public String getImage() {
        return b64Image;
    }

    @Override
    public String toString() {
        if (category == null)
            return nombre;
        else
            return nombre + " (" + category.getName() + ")";
    }
}
