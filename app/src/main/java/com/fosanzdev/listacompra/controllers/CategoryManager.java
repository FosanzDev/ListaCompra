package com.fosanzdev.listacompra.controllers;

import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.db.dao.CategoryDAO;
import com.fosanzdev.listacompra.models.Category;
import com.fosanzdev.listacompra.models.ItemViewFittable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryManager extends ArrayList<Category> implements Serializable {

    private final SQLiteDatabase db;

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

    public Category getCategory(int id) {
        for (Category category : this) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
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

    @Override
    public boolean addAll(Collection<? extends Category> c) {
        boolean result = super.addAll(c);
        if (result) {
            for (Category category : c) {
                new CategoryDAO(db).insert(category);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Category category : this) {
            sb.append(category.toString()).append("\n");
        }
        return sb.toString();
    }

    public List<ItemViewFittable> getItems() {
        return new ArrayList<>(this);
    }
}
