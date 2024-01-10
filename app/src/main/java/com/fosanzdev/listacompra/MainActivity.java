package com.fosanzdev.listacompra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

import com.fosanzdev.listacompra.controllers.CategoryManager;
import com.fosanzdev.listacompra.controllers.ItemManager;
import com.fosanzdev.listacompra.controllers.ShoppingListManager;
import com.fosanzdev.listacompra.db.ShoppingListSQLiteHelper;
import com.fosanzdev.listacompra.models.Category;
import com.fosanzdev.listacompra.models.Item;
import com.fosanzdev.listacompra.models.ShoppingList;
import com.fosanzdev.listacompra.ui.ShoppingListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;

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

        if (!ShoppingListSQLiteHelper.initialized){
            ArrayList<Category> categories = new ArrayList<>();
            categories.add(new Category("Frutas", "img"));
            categories.add(new Category("Verduras", "img"));
            categories.add(new Category("Carnes", "img"));
            categories.add(new Category("Pescados", "img"));
            categories.add(new Category("Lácteos", "img"));
            categories.add(new Category("Bebidas", "img"));
            categories.add(new Category("Droguería", "img"));
            categories.add(new Category("Higiene", "img"));
            categories.add(new Category("Otros", "img"));
            categoryManager.addAll(categories);

            ArrayList<Item> items = new ArrayList<>();
            items.add(new Item("Manzana", categoryManager.get(0), "img"));
            items.add(new Item("Pera", categoryManager.get(0), "img"));
            items.add(new Item("Plátano", categoryManager.get(0), "img"));

            items.add(new Item("Lechuga", categoryManager.get(1), "img"));
            items.add(new Item("Tomate", categoryManager.get(1), "img"));
            items.add(new Item("Cebolla", categoryManager.get(1), "img"));

            items.add(new Item("Pollo", categoryManager.get(2), "img"));
            items.add(new Item("Ternera", categoryManager.get(2), "img"));
            items.add(new Item("Cerdo", categoryManager.get(2), "img"));

            items.add(new Item("Salmón", categoryManager.get(3), "img"));
            items.add(new Item("Bacalao", categoryManager.get(3), "img"));
            items.add(new Item("Atún", categoryManager.get(3), "img"));

            items.add(new Item("Leche", categoryManager.get(4), "img"));
            items.add(new Item("Queso", categoryManager.get(4), "img"));
            items.add(new Item("Yogur", categoryManager.get(4), "img"));

            items.add(new Item("Agua", categoryManager.get(5), "img"));
            items.add(new Item("Cerveza", categoryManager.get(5), "img"));
            items.add(new Item("Vino", categoryManager.get(5), "img"));

            items.add(new Item("Detergente", categoryManager.get(6), "img"));
            items.add(new Item("Suavizante", categoryManager.get(6), "img"));
            items.add(new Item("Lejía", categoryManager.get(6), "img"));

            items.add(new Item("Papel higiénico", categoryManager.get(7), "img"));
            items.add(new Item("Gel", categoryManager.get(7), "img"));
            items.add(new Item("Champú", categoryManager.get(7), "img"));

            items.add(new Item("Pan", categoryManager.get(8), "img"));
            items.add(new Item("Huevos", categoryManager.get(8), "img"));
            items.add(new Item("Arroz", categoryManager.get(8), "img"));

            itemManager.addAll(items);

            System.out.println("Initialized");
        }

        // -----------------------------------
        // FLOATING ACTION BUTTON (FAB) SETUP
        // This will create a new ShoppingList
        // -----------------------------------
        FloatingActionButton fabAddShoppingList = findViewById(R.id.fabAddShoppingList);
        fabAddShoppingList.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nueva lista de la compra");
            builder.setView(R.layout.new_shopping_list_dialog);
            EditText etShoppingListName = findViewById(R.id.etShoppingListTitle);

            builder.setPositiveButton("Crear", (dialog, which) -> {
                String shoppingListName = etShoppingListName.getText().toString();
                ShoppingList shoppingList = new ShoppingList(shoppingListName);
                manager.add(shoppingList);
            });

            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        RecyclerView rvShoppingLists = findViewById(R.id.rvShoppingLists);
        rvShoppingLists.setAdapter(new ShoppingListAdapter(manager));
        rvShoppingLists.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
}