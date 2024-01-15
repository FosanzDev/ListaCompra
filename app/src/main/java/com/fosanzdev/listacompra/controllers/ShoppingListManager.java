package com.fosanzdev.listacompra.controllers;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.fosanzdev.listacompra.db.dao.ShoppingListDAO;
import com.fosanzdev.listacompra.models.Item;
import com.fosanzdev.listacompra.models.ShoppingList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShoppingListManager extends ArrayList<ShoppingList> implements Serializable {

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
            }
        }
    }

    public ShoppingList getShoppingList(int id) {
        for (ShoppingList shoppingList : this) {
            if (shoppingList.getId() == id) {
                return shoppingList;
            }
        }
        return null;
    }

    public void addItem(ShoppingList shoppingList, Item item) {
        shoppingList.addItem(item);
        new ShoppingListDAO(db).insertItem(shoppingList, item);
    }

    public void removeItem(ShoppingList shoppingList, Item item) {
        shoppingList.removeItem(item);
        new ShoppingListDAO(db).deleteItem(shoppingList, item);
    }

    public void removeAllItems(ShoppingList shoppingList) {
        shoppingList.getItems().clear();
        new ShoppingListDAO(db).deleteAllItems(shoppingList);
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
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
        if (result) {
            new ShoppingListDAO(db).insert(shoppingList);
        }
        return result;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends ShoppingList> c) {
        boolean result = super.addAll(c);
        if (result) {
            for (ShoppingList shoppingList : c) {
                new ShoppingListDAO(db).insert(shoppingList);
            }
        }
        return result;
    }
}
