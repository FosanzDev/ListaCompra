package com.fosanzdev.listacompra.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryDAO extends DAO<Category> {

    private static final String TABLE_NAME = "Categories";


    public CategoryDAO(SQLiteDatabase db) {
        super(TABLE_NAME, db);
    }

    @Override
    public Category findById(int id) {
        String [] args = new String[]{String.valueOf(id)};
        try (Cursor c = db.rawQuery("SELECT * FROM Categories WHERE id = ?", args)) {
            if (c.moveToFirst()) {
                String nombre = c.getString(columnIndex.get("nombre"));
                String b64Image = c.getString(columnIndex.get("b64Image"));
                return new Category(id, nombre, b64Image);
            }
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Cursor c = db.rawQuery("SELECT * FROM Categories", null)) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(columnIndex.get("id"));
                    String nombre = c.getString(columnIndex.get("nombre"));
                    String b64Image = c.getString(columnIndex.get("b64Image"));
                    categories.add(new Category(id, nombre, b64Image));
                } while (c.moveToNext());
            }
        }
        return categories;
    }

    @Override
    public List<Category> findBy(Map<String, String> condition) {
        List<Category> categories = new ArrayList<>();
        String[] args = new String[condition.size()];
        int i = 0;
        StringBuilder query = new StringBuilder("SELECT * FROM Categories WHERE ");
        for (Map.Entry<String, String> entry : condition.entrySet()) {
            query.append(entry.getKey()).append(" = ? AND ");
            args[i] = entry.getValue();
            i++;
        }
        query.delete(query.length() - 5, query.length());
        try (Cursor c = db.rawQuery(query.toString(), args)) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(columnIndex.get("id"));
                    String nombre = c.getString(columnIndex.get("nombre"));
                    String b64Image = c.getString(columnIndex.get("b64Image"));
                    categories.add(new Category(id, nombre, b64Image));
                } while (c.moveToNext());
            }
        }
        return categories;
    }

    @Override
    public boolean update(Category category) {
        String query = "UPDATE Categories SET nombre = ?, b64Image = ? WHERE id = ?";
        String[] args = new String[]{category.getName(), category.getName(), String.valueOf(category.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            return c.moveToFirst();
        }
    }

    @Override
    public boolean delete(Category category) {
        String query = "DELETE FROM Categories WHERE id = ?";
        String[] args = new String[]{String.valueOf(category.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            return c.moveToFirst();
        }
    }

    @Override
    public boolean insert(Category category) {
        String query = "INSERT INTO Categories (nombre, b64Image) VALUES (?, ?)";
        String[] args = new String[]{category.getName(), category.getName()}  ;
        try (Cursor c = db.rawQuery(query, args)) {
            query = "SELECT id from Categories ORDER BY id DESC LIMIT 1";
            try (Cursor c2 = db.rawQuery(query, null)) {
                if (c2.moveToFirst()) {
                    category.setId(c2.getInt(0));
                }
            }
            return c.moveToFirst();
        }
    }
}
