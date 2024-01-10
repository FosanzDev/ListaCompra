package com.fosanzdev.listacompra.models;

import com.fosanzdev.listacompra.controllers.ShoppingListManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingList {

    private int id;
    private List<Item> items = new ArrayList<>();
    private String nombre;
    private Date date;
    private ShoppingListManager manager;

    //Empty constructor
    public ShoppingList() {
    }

    public ShoppingList(String nombre) {
        this.nombre = nombre;
        this.date = new Date();
    }

    public ShoppingList(int id, List<Item> items, String nombre, Date date) {
        this.id = id;
        this.items = items;
        this.nombre = nombre;
        this.date = date;
    }

    public ShoppingList(int id, List<Item> items, String nombre, ShoppingListManager manager) {
        this.id = id;
        this.items = items;
        this.nombre = nombre;
        this.manager = manager;
    }

    public void setManager(ShoppingListManager manager) {
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        if (manager != null) {
            manager.addItem(this, item);
        }
    }

    public void removeItem(Item item){
        items.remove(item);
        if (manager != null) {
            manager.deleteItem(this, item);
        }
    }

    public void removeAllItems(){
        items.clear();
        if (manager != null) {
            manager.deleteAllItems(this);
        }
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nombre);
        sb.append(" (");
        sb.append(items.size());
        sb.append(" items)");
        sb.append(" created: ");
        sb.append(date.toString());
        sb.append("\n");
        return sb.toString();
    }
}
