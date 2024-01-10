package com.fosanzdev.listacompra.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.models.Item;
import com.fosanzdev.listacompra.models.ShoppingList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingListDAO extends DAO<ShoppingList>{

    private static final String TABLE_NAME = "ShoppingList";

    private final HashMap<String, Integer> shoppingListItemsColumnIndex = super.fillColumnIndex("ShoppingListItems");

    public ShoppingListDAO(SQLiteDatabase db) {
        super(TABLE_NAME, db);
        System.out.println("ShoppingListDAO created");
        System.out.println(columnIndex);
    }

    /**
     * Returns a ShoppingList Object from the database with the given id
     * @param id id of the ShoppingList
     * @return ShoppingList Object
     */
    @Override
    public ShoppingList findById(int id) {
        String [] args = new String[]{String.valueOf(id)};
        try (Cursor c = db.rawQuery("SELECT * FROM ShoppingList WHERE id = ?", args)) {
            if (c.moveToFirst()) {
                String nombre = c.getString(columnIndex.get("nombre"));
                List<Item> items = getAllItemsById(id);
                Date date = new Date(c.getLong(columnIndex.get("date")));
                return new ShoppingList(id, items, nombre, date);
            }
        }
        return null;
    }

    /**
     * Returns a List of all ShoppingList Objects in the database
     * @return List of ShoppingList Objects
     */
    @Override
    public List<ShoppingList> findAll() {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        try (Cursor c = db.rawQuery("SELECT * FROM ShoppingList", null)) {
            if (c.moveToFirst()) {
                do {
                    String nombre = c.getString(columnIndex.get("nombre"));
                    int id = c.getInt(columnIndex.get("id"));
                    List<Item> items = getAllItemsById(id);
                    String stringDate = c.getString(columnIndex.get("date"));
                    //String with format yyyy/MM/dd HH:mm:ss to Date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = sdf.parse(stringDate);
                    shoppingLists.add(new ShoppingList(id, items, nombre, date));
                } while (c.moveToNext());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return shoppingLists;
    }

    @Override
    public List<ShoppingList> findBy(Map<String, String> condition) {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        String[] args = new String[condition.size()];
        int i = 0;
        StringBuilder query = new StringBuilder("SELECT * FROM ShoppingList WHERE ");
        for (Map.Entry<String, String> entry : condition.entrySet()) {
            query.append(entry.getKey()).append(" = ? AND ");
            args[i] = entry.getValue();
            i++;
        }
        //Delete last AND
        query.delete(query.length() - 5, query.length());
        try (Cursor c = db.rawQuery(query.toString(), args)) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(columnIndex.get("id"));
                    List<Item> items = getAllItemsById(id);
                    String nombre = c.getString(columnIndex.get("nombre"));
                    String stringDate = c.getString(columnIndex.get("date"));
                    //String with format yyyy/MM/dd HH:mm:ss to Date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = sdf.parse(stringDate);
                    shoppingLists.add(new ShoppingList(id, items, nombre, date));
                } while (c.moveToNext());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return shoppingLists;
    }

    @Override
    public boolean update(ShoppingList shoppingList) {
        String query = "UPDATE ShoppingList SET nombre = ?, date = ? WHERE id = ?";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String stringDate = sdf.format(shoppingList.getDate());
        String[] args = new String[]{shoppingList.getNombre(), stringDate, String.valueOf(shoppingList.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            c.moveToFirst();
        }
        deleteAllItems(shoppingList);
        insertAllItems(shoppingList, shoppingList.getItems());
        return true;
    }

    @Override
    public boolean delete(ShoppingList shoppingList) {
        String query = "DELETE FROM ShoppingList WHERE id = ?";
        String[] args = new String[]{String.valueOf(shoppingList.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            deleteAllItems(shoppingList);
            return c.moveToFirst();
        }
    }

    @Override
    public boolean insert(ShoppingList shoppingList) {
        String query = "INSERT INTO ShoppingList (nombre, date) VALUES (?, ?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String stringDate = sdf.format(shoppingList.getDate());
        String[] args = new String[]{shoppingList.getNombre(), stringDate};
        try (Cursor c = db.rawQuery(query, args)) {
            insertAllItems(shoppingList, shoppingList.getItems());
            return c.moveToFirst();
        }
    }

    public List<Item> getAllItemsById(int shoppingListId){
        String query = "SELECT * FROM ShoppingListItems WHERE fk_shopping_list = ?";
        String[] args = new String[]{String.valueOf(shoppingListId)};
        try (Cursor c = db.rawQuery(query, args)) {
            List<Item> items = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    items.add(new ItemDAO(db).findById(c.getInt(shoppingListItemsColumnIndex.get("fk_item"))));
                } while (c.moveToNext());
            }
            return items;
        }
    }

    public boolean insertItem(ShoppingList shoppingList, Item item){
        String query = "INSERT INTO ShoppingListItems (fk_shopping_list, fk_item) VALUES (?, ?)";
        String[] args = new String[]{String.valueOf(shoppingList.getId()), String.valueOf(item.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            return c.moveToFirst();
        }
    }

    public boolean insertAllItems(ShoppingList shoppingList, List<Item> items){
        String query = "INSERT INTO ShoppingListItems (fk_shopping_list, fk_item) VALUES (?, ?)";
        if (items == null) {
            return false;
        }
        for (Item item : items) {
            String[] args = new String[]{String.valueOf(shoppingList.getId()), String.valueOf(item.getId())};
            try (Cursor c = db.rawQuery(query, args)) {
                c.moveToFirst();
            }
        }
        return true;
    }

    public boolean deleteItem(ShoppingList shoppingList, Item item){
        String query = "DELETE FROM ShoppingListItems WHERE fk_shopping_list = ? AND fk_item = ?";
        String[] args = new String[]{String.valueOf(shoppingList.getId()), String.valueOf(item.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            return c.moveToFirst();
        }
    }

    public boolean deleteAllItems(ShoppingList shoppingList){
        String query = "DELETE FROM ShoppingListItems WHERE fk_shopping_list = ?";
        String[] args = new String[]{String.valueOf(shoppingList.getId())};
        try (Cursor c = db.rawQuery(query, args)) {
            return c.moveToFirst();
        }
    }
}
