package com.fosanzdev.listacompra;

import com.fosanzdev.listacompra.db.dao.ShoppingListDAO;

import java.util.List;

public class ShoppingList {

    private int id;
    private List<Item> items;
    private String nombre;
    private ShoppingListManager manager;

    //Empty constructor
    public ShoppingList() {
    }

    public ShoppingList(String nombre, ShoppingListManager manager) {
        this.nombre = nombre;
        this.manager = manager;
    }

    public ShoppingList(int id, List<Item> items, String nombre) {
        this.id = id;
        this.items = items;
        this.nombre = nombre;
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
}
