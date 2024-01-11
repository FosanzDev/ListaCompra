package com.fosanzdev.listacompra.controllers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.db.dao.ItemDAO;
import com.fosanzdev.listacompra.models.Category;
import com.fosanzdev.listacompra.models.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemManager extends ArrayList<Item> {

    private SQLiteDatabase db;

    public ItemManager(SQLiteDatabase db) {
        super();
        this.db = db;
        init();
    }

    /**
     * Reads from the database all the items and adds them to the manager
     */
    private void init(){
        ItemDAO dao = new ItemDAO(db);
        String getLastIndexQuery = "SELECT MAX(id) FROM Items";
        Cursor c = db.rawQuery(getLastIndexQuery, null);
        if (c.moveToFirst()) {
            int lastIndex = c.getInt(0);
            for (int i = 1; i <= lastIndex; i++) {
                Item item = dao.findById(i);
                if (item != null) {
                    addSilent(item);
                }
            }
        }
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        if (result) {
            new ItemDAO(db).delete((Item) o);
        }
        return result;
    }

    private void addSilent(Item item) {
        super.add(item);
    }

    @Override
    public boolean add(Item item) {
        boolean result = super.add(item);
        if (result) {
            new ItemDAO(db).insert(item);
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends Item> c) {
        boolean result = super.addAll(c);
        if (result) {
            for (Item item : c) {
                new ItemDAO(db).insert(item);
            }
        }
        return result;
    }

    public List<Item> getItemsByCategory(Category category) {
        List<Item> itemsByCategory = new ArrayList<>();
        for (Item item : this) {
            if (item.getCategory() == category) {
                itemsByCategory.add(item);
            }
        }
        return itemsByCategory;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }
}
