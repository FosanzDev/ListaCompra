package com.fosanzdev.listacompra.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ShoppingListSQLiteHelper extends SQLiteOpenHelper {
    private static ShoppingListSQLiteHelper instance;
    private static final String DATABASE_NAME = "shoppinglist.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_CATEGORIES =
            "CREATE TABLE Categories (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "b64Image TEXT NOT NULL" +
                    ");";

    private static final String SQL_CREATE_TABLE_ITEMS =
            "CREATE TABLE Items (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "fk_category INTEGER NOT NULL," +
                    "b64Image TEXT NOT NULL," +
                    "FOREIGN KEY (fk_category) REFERENCES Categories(id)" +
                    ");";

    private static final String SQL_CREATE_TABLE_SHOPPING_LIST =
            "CREATE TABLE ShoppingList (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "date DATE NOT NULL" +
                    ");";

    private static final String SQL_CREATE_TABLE_SHOPPING_LIST_ITEMS =
            "CREATE TABLE ShoppingListItems (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "fk_shopping_list INTEGER NOT NULL," +
                    "fk_item INTEGER NOT NULL," +
                    "FOREIGN KEY (fk_shopping_list) REFERENCES ShoppingList(id)," +
                    "FOREIGN KEY (fk_item) REFERENCES Items(id)" +
                    ");";


    public static synchronized ShoppingListSQLiteHelper getInstance(@Nullable Context context) {
        if (instance == null) {
            instance = new ShoppingListSQLiteHelper(context);
        }
        return instance;
    }

    private ShoppingListSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CATEGORIES);
        db.execSQL(SQL_CREATE_TABLE_ITEMS);
        db.execSQL(SQL_CREATE_TABLE_SHOPPING_LIST);
        db.execSQL(SQL_CREATE_TABLE_SHOPPING_LIST_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //OnUpgrade implementation
    }
}
