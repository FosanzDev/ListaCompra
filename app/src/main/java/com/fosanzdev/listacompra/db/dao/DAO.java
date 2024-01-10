package com.fosanzdev.listacompra.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DAO<T> {

    protected SQLiteDatabase db;
    protected final HashMap<String, Integer> columnIndex;

    DAO(String tableName) {
        this.tableName = tableName;
        columnIndex = new HashMap<>();
        fillColumnIndex();
    }

    private void fillColumnIndex() {
        Cursor c = db.rawQuery("SELECT * FROM " + tableName, null);
        for (String columnName : c.getColumnNames()) {
            columnIndex.put(columnName, c.getColumnIndex(columnName));
        }
    }

    private String tableName;

    public abstract T findById(int id);
    public abstract List<T> findAll();
    public abstract List<T> findBy(Map<String, String> condition);
    public abstract boolean update(T t);
    public abstract boolean delete(T t);
    public abstract boolean insert(T t);

    public String getTableName() {
        return tableName;
    }
}
