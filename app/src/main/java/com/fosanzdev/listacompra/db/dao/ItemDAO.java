package com.fosanzdev.listacompra.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.Category;
import com.fosanzdev.listacompra.Item;

import java.util.List;
import java.util.Map;

public class ItemDAO extends DAO<Item> {
    private static final String TABLE_NAME = "Items";

    public ItemDAO(SQLiteDatabase db) {
        super(TABLE_NAME);
    }

    @Override
    public Item findById(int id) {
        String[] args = new String[]{String.valueOf(id)};
        try (Cursor c = db.rawQuery("SELECT * FROM Items WHERE id = ?", args)) {
            if (c.moveToFirst()) {
                String nombre = c.getString(columnIndex.get("nombre"));
                Category category = new CategoryDAO(db).findById(c.getInt(columnIndex.get("fk_category")));
                String b64Image = c.getString(columnIndex.get("b64Image"));
                return new Item(id, nombre, category, b64Image);
            }
        }
        return null;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = null;
        try (Cursor c = db.rawQuery("SELECT * FROM Items", null)) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(columnIndex.get("id"));
                    String nombre = c.getString(columnIndex.get("nombre"));
                    Category category = new CategoryDAO(db).findById(c.getInt(columnIndex.get("fk_category")));
                    String b64Image = c.getString(columnIndex.get("b64Image"));
                    items.add(new Item(id, nombre, category, b64Image));
                } while (c.moveToNext());
            }
        }
        return items;
    }

    @Override
    public List<Item> findBy(Map<String, String> condition) {
        List<Item> items = null;
        String[] args = new String[condition.size()];
        int i = 0;
        StringBuilder query = new StringBuilder("SELECT * FROM Items WHERE ");
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
                    Category category = new CategoryDAO(db).findById(c.getInt(columnIndex.get("fk_category")));
                    String b64Image = c.getString(columnIndex.get("b64Image"));
                    items.add(new Item(id, nombre, category, b64Image));
                } while (c.moveToNext());
            }
        }
        return items;
    }

    @Override
    public boolean update(Item item) {
        String query = "UPDATE Items SET nombre = ?, fk_category = ?, b64Image = ? WHERE id = ?";
        String[] args = new String[]{item.getNombre(), String.valueOf(item.getCategory().getId()), item.getB64Image(), String.valueOf(item.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            return c.moveToFirst();
        }
    }

    @Override
    public boolean delete(Item item) {
        String query = "DELETE FROM Items WHERE id = ?";
        String[] args = new String[]{String.valueOf(item.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            return c.moveToFirst();
        }
    }

    @Override
    public boolean insert(Item item) {
        String query = "INSERT INTO Items (nombre, fk_category, b64Image) VALUES (?, ?, ?)";
        String[] args = new String[]{item.getNombre(), String.valueOf(item.getCategory().getId()), item.getB64Image()};
        try (Cursor c = db.rawQuery(query, args)) {
            return c.moveToFirst();
        }
    }
}