package com.fosanzdev.listacompra.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.db.dao.CategoryDAO;
import com.fosanzdev.listacompra.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryManager extends ArrayList<Category> {

    private SQLiteDatabase db;

    public CategoryManager(SQLiteDatabase db) {
        super();
        this.db = db;
        init();
    }

    /**
     * Reads from the database all the categories and adds them to the manager
     */
    private void init() {
        CategoryDAO dao = new CategoryDAO(db);
        List<Category> categories = dao.findAll();
        if (categories != null) {
            for (Category category : categories) {
                addSilent(category);
            }
        }
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        if (result) {
            new CategoryDAO(db).delete((Category) o);
        }
        return result;
    }

    private void addSilent(Category category) {
        super.add(category);
    }

    @Override
    public boolean add(Category category) {
        boolean result = super.add(category);
        if (result) {
            new CategoryDAO(db).insert(category);
        }
        return result;
    }
}
