package com.fosanzdev.listacompra;

import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.db.dao.ShoppingListDAO;

import java.util.ArrayList;

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
        for (ShoppingList shoppingList : dao.findAll()) {
            add(shoppingList);
            shoppingList.setManager(this);
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
        if (result) {
            new ShoppingListDAO(db).delete((ShoppingList) o);
        }
        return result;
    }
}
