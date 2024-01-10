package com.fosanzdev.listacompra;

import com.fosanzdev.listacompra.db.dao.ShoppingListDAO;

import java.util.List;

public class ShoppingList {

    private int id;
    private List<Item> items;
    private String nombre;

    //Empty constructor
    public ShoppingList() {

    }

    public ShoppingList(int id, List<Item> items, String nombre) {
        this.id = id;
        this.items = items;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        new ShoppingListDAO("ShoppingList").insertItem(this, item);
    }

    public void removeItem(Item item){
        items.remove(item);
        new ShoppingListDAO("ShoppingList").deleteItem(this, item);
    }

    public void removeAllItems(){
        items.clear();
        new ShoppingListDAO("ShoppingList").deleteAllItems(this);
    }

    public String getNombre() {
        return nombre;
    }
}
