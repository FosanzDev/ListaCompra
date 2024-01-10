package com.fosanzdev.listacompra.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.db.dao.ShoppingListDAO;
import com.fosanzdev.listacompra.models.Item;
import com.fosanzdev.listacompra.models.ShoppingList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShoppingListManager extends ArrayList<ShoppingList> {

    private SQLiteDatabase db;

    public ShoppingListManager(SQLiteDatabase db) {
        super();
        this.db = db;
        init();
    }

    /**
     * Reads from the database all the shopping lists and adds them to the manager
     */
    private void init(){
        ShoppingListDAO dao = new ShoppingListDAO(db);
        List<ShoppingList> shoppingLists = dao.findAll();
        if (shoppingLists != null) {
            for (ShoppingList shoppingList : shoppingLists) {
                addSilent(shoppingList);
                shoppingList.setManager(this);
            }
        }
    }

    public void addItem(ShoppingList shoppingList, Item item) {
        new ShoppingListDAO(db).insertItem(shoppingList, item);
    }

    public void deleteItem(ShoppingList shoppingList, Item item) {
        new ShoppingListDAO(db).deleteItem(shoppingList, item);
    }

    public void deleteAllItems(ShoppingList shoppingList) {
        new ShoppingListDAO(db).deleteAllItems(shoppingList);
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        ((ShoppingList)o).setManager(null);
        if (result) {
            new ShoppingListDAO(db).delete((ShoppingList) o);
        }
        return result;
    }

    private void addSilent(ShoppingList shoppingList) {
        super.add(shoppingList);
    }

    @Override
    public boolean add(ShoppingList shoppingList) {
        boolean result = super.add(shoppingList);
        shoppingList.setManager(this);
        if (result) {
            new ShoppingListDAO(db).insert(shoppingList);
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends ShoppingList> c) {
        boolean result = super.addAll(c);
        if (result) {
            for (ShoppingList shoppingList : c) {
                shoppingList.setManager(this);
                new ShoppingListDAO(db).insert(shoppingList);
            }
        }
        return result;
    }
}
