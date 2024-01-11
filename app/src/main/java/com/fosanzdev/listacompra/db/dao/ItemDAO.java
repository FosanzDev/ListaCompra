package com.fosanzdev.listacompra.db.dao;

import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.fosanzdev.listacompra.Utils;
import com.fosanzdev.listacompra.models.Category;
import com.fosanzdev.listacompra.models.Item;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemDAO extends DAO<Item> {
    private static final String TABLE_NAME = "Items";
    private static final int CHUNK_SIZE = 1024;

    public ItemDAO(SQLiteDatabase db) {
        super(TABLE_NAME, db);
    }

    @Override
    public Item findById(int id) {
        String[] args = new String[]{String.valueOf(id)};
        try (Cursor c = db.rawQuery("SELECT * FROM Items WHERE id = ?", args)) {
            if (c.moveToFirst()) {
                String nombre = c.getString(columnIndex.get("nombre"));
                Category category = new CategoryDAO(db).findById(c.getInt(columnIndex.get("fk_category")));
                byte[] image = retrieveImageFromId(id);
                return new Item(id, nombre, category, image);
            }
        }
        return null;
    }

    @Override
    public List<Item> findAll() {
        System.out.println("findAll");
        List<Item> items = new ArrayList<>();
        try (Cursor c = db.rawQuery("SELECT * FROM Items", null)) {
            if (c.moveToFirst()) {
                System.out.println("Has next");
                do {
                    int id = c.getInt(columnIndex.get("id"));
                    String nombre = c.getString(columnIndex.get("nombre"));
                    Category category = new CategoryDAO(db).findById(c.getInt(columnIndex.get("fk_category")));
                    byte[] image = retrieveImageFromId(id);
                    items.add(new Item(id, nombre, category, image));
                } while (c.moveToNext());
            }
        }
        return items;
    }

    private byte[] retrieveImageFromId(int id){
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        String query = "SELECT imageChunk FROM ItemImages WHERE fk_item = ? ORDER BY chunkIndex";
        String[] args = new String[]{String.valueOf(id-1)}; //TODO: FIX THISSSS
        try (Cursor c = db.rawQuery(query, args)) {
            while (c.moveToNext()) {
                byte[] chunk = c.getBlob(0);
                imageStream.write(chunk, 0, chunk.length);
            }
        }
        return imageStream.toByteArray();
    }

    @Override
    public List<Item> findBy(Map<String, String> condition) {
        List<Item> items = new ArrayList<>();
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
                    byte[] image = retrieveImageFromId(id); //c.getString(columnIndex.get("b64Image"));
                    items.add(new Item(id, nombre, category, image));
                } while (c.moveToNext());
            }
        }
        return items;
    }

    @Override
    public boolean update(Item item) {
        String query = "UPDATE Items SET nombre = ?, fk_category = ? WHERE id = ?";
        String[] args = new String[]{item.getName(), String.valueOf(item.getCategory().getId()), String.valueOf(item.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            // Delete old image chunks
            String deleteImageQuery = "DELETE FROM ItemImages WHERE fk_item = ?";
            String[] deleteImageQueryArgs = new String[]{String.valueOf(item.getId())};
            db.execSQL(deleteImageQuery, deleteImageQueryArgs);

            // Insert new image chunks
            byte[] imageBytes = item.getImage();
            int chunkIndex = 0;
            while (chunkIndex < imageBytes.length) {
                int end = Math.min(chunkIndex + CHUNK_SIZE, imageBytes.length);
                byte[] chunk = Arrays.copyOfRange(imageBytes, chunkIndex, end);

                String insertImageQuery = "INSERT INTO ItemImages (fk_item, chunkIndex, imageChunk) VALUES (?, ?, ?)";
                String[] insertImageQueryArgs = new String[]{String.valueOf(item.getId()), String.valueOf(chunkIndex), Base64.encodeToString(chunk, Base64.DEFAULT)};
                db.execSQL(insertImageQuery, insertImageQueryArgs);
                chunkIndex += CHUNK_SIZE;
            }

            return c.moveToFirst();
        }
    }

    @Override
    public boolean delete(Item item) {
        String query = "DELETE FROM Items WHERE id = ?";
        String[] args = new String[]{String.valueOf(item.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            String deleteImageQuery = "DELETE FROM ItemImages WHERE fk_item = ?";
            String[] deleteImageArgs = new String[]{String.valueOf(item.getId())};
            db.execSQL(deleteImageQuery, deleteImageArgs);
            return c.moveToFirst();
        }
    }

    @Override
    public boolean insert(Item item) {
        String query = "INSERT INTO Items (nombre, fk_category) VALUES (?, ?)";
        String[] args = new String[]{item.getName(), String.valueOf(item.getCategory().getId())};
        try (Cursor c = db.rawQuery(query, args)) {

            query = "SELECT id FROM Items ORDER BY id DESC LIMIT 1";
            try (Cursor c2 = db.rawQuery(query, null)) {
                if (c2.moveToFirst()) {
                    item.setId(c2.getInt(0));
                }
            }

            // Split the image into chunks and store each chunk in a separate row
            byte[] imageBytes = item.getImage();
            int chunkIndex = 0;
            while (chunkIndex < imageBytes.length) {
                int end = Math.min(chunkIndex + CHUNK_SIZE, imageBytes.length);
                byte[] chunk = Arrays.copyOfRange(imageBytes, chunkIndex, end);

                String insertImageQuery = "INSERT INTO ItemImages (fk_item, chunkIndex, imageChunk) VALUES (?, ?, ?)";
                String[] insertImageQueryArgs = new String[]{String.valueOf(item.getId()), String.valueOf(chunkIndex), Base64.encodeToString(chunk, Base64.DEFAULT)};
                db.execSQL(insertImageQuery, insertImageQueryArgs);
                chunkIndex += CHUNK_SIZE;
            }

            return c.moveToFirst();
        }
    }
}
