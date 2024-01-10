package com.fosanzdev.listacompra.db.dao;

import android.database.Cursor;

import com.fosanzdev.listacompra.Item;
import com.fosanzdev.listacompra.ShoppingList;

import java.util.List;
import java.util.Map;

public class ShoppingListDAO extends DAO<ShoppingList>{

    private static final String TABLE_NAME = "ShoppingList";

    public ShoppingListDAO(String tableName) {
        super(TABLE_NAME);
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
                return new ShoppingList(id, items, nombre);
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
        List<ShoppingList> shoppingLists = null;
        try (Cursor c = db.rawQuery("SELECT * FROM ShoppingList", null)) {
            if (c.moveToFirst()) {
                do {
                    String nombre = c.getString(columnIndex.get("nombre"));
                    int id = c.getInt(columnIndex.get("id"));
                    List<Item> items = getAllItemsById(id);
                    shoppingLists.add(new ShoppingList(id, items, nombre));
                } while (c.moveToNext());
            }
        }
        return shoppingLists;
    }

    @Override
    public List<ShoppingList> findBy(Map<String, String> condition) {
        List<ShoppingList> shoppingLists = null;
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
                    shoppingLists.add(new ShoppingList(id, items, nombre));
                } while (c.moveToNext());
            }
        }
        return shoppingLists;
    }

    @Override
    public boolean update(ShoppingList shoppingList) {
        String query = "UPDATE ShoppingList SET nombre = ? WHERE id = ?";
        String[] args = new String[]{shoppingList.getNombre(), String.valueOf(shoppingList.getId())};
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
        String query = "INSERT INTO ShoppingList (nombre) VALUES (?)";
        String[] args = new String[]{shoppingList.getNombre()};
        try (Cursor c = db.rawQuery(query, args)) {
            insertAllItems(shoppingList, shoppingList.getItems());
            return c.moveToFirst();
        }
    }

    public List<Item> getAllItemsById(int shoppingListId){
        String query = "SELECT * FROM ShoppingListItems WHERE fk_shopping_list = ?";
        String[] args = new String[]{String.valueOf(shoppingListId)};
        try (Cursor c = db.rawQuery(query, args)) {
            List<Item> items = null;
            if (c.moveToFirst()) {
                do {
                    items.add(new ItemDAO(db).findById(c.getInt(columnIndex.get("fk_item"))));
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
