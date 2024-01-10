package com.fosanzdev.listacompra;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.fosanzdev.listacompra.controllers.CategoryManager;
import com.fosanzdev.listacompra.controllers.ItemManager;
import com.fosanzdev.listacompra.controllers.ShoppingListManager;
import com.fosanzdev.listacompra.db.ShoppingListSQLiteHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShoppingListSQLiteHelper helper = ShoppingListSQLiteHelper.getInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ShoppingListManager manager = new ShoppingListManager(db);
        ItemManager itemManager = new ItemManager(db);
        CategoryManager categoryManager = new CategoryManager(db);

        TextView textView = findViewById(R.id.tvLog);
        textView.setText(manager.toString());
    }
}