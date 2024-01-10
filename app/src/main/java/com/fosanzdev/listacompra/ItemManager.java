package com.fosanzdev.listacompra;

import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.db.dao.ItemDAO;

import java.util.ArrayList;

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
        for (Item item : dao.findAll()) {
            add(item);
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

    @Override
    public boolean add(Item item) {
        boolean result = super.add(item);
        if (result) {
            new ItemDAO(db).insert(item);
        }
        return result;
    }
}
