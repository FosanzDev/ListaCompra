package com.fosanzdev.listacompra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.fosanzdev.listacompra.controllers.CategoryManager;
import com.fosanzdev.listacompra.controllers.ItemManager;
import com.fosanzdev.listacompra.controllers.ShoppingListManager;
import com.fosanzdev.listacompra.db.ShoppingListSQLiteHelper;
import com.fosanzdev.listacompra.models.Category;
import com.fosanzdev.listacompra.models.Item;
import com.fosanzdev.listacompra.models.ShoppingList;
import com.fosanzdev.listacompra.ui.ShoppingListAdapter;
import com.fosanzdev.listacompra.ui.fragments.ShoppingListDetailFragment;
import com.fosanzdev.listacompra.ui.fragments.ShoppingListsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ShoppingListsFragment.IShoppingListFragmentListener, ShoppingListDetailFragment.IShoppingListDetailFragmentListener, ShoppingListAdapter.ShoppingListViewHolder.IOnShoppingListClickListener {

    ShoppingListSQLiteHelper helper;
    SQLiteDatabase db;
    private ShoppingListManager manager;
    private ItemManager itemManager;
    private CategoryManager categoryManager;
    FragmentContainerView fcvContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (manager == null) {
            init();
        }

        if (!ShoppingListSQLiteHelper.initialized) {
            ArrayList<Category> categories = new ArrayList<>();
            categories.add(new Category("Frutas", Utils.webpToByteArray(R.drawable.frutas, this)));
            categories.add(new Category("Verduras", Utils.webpToByteArray(R.drawable.verduras, this)));
            categories.add(new Category("Carnes", Utils.webpToByteArray(R.drawable.carnes, this)));
            categories.add(new Category("Pescados", Utils.webpToByteArray(R.drawable.pescados, this)));
            categories.add(new Category("Lácteos", Utils.webpToByteArray(R.drawable.lacteos, this)));
            categories.add(new Category("Bebidas", Utils.webpToByteArray(R.drawable.bebidas, this)));
            categories.add(new Category("Droguería", Utils.webpToByteArray(R.drawable.drogueria, this)));
            categories.add(new Category("Higiene", Utils.webpToByteArray(R.drawable.higiene, this)));
            categories.add(new Category("Otros", Utils.webpToByteArray(R.drawable.otros, this)));
            categoryManager.addAll(categories);

            ArrayList<Item> items = new ArrayList<>();
            items.add(new Item("Manzana", categoryManager.get(0), Utils.webpToByteArray(R.drawable.manzana, this)));
            items.add(new Item("Pera", categoryManager.get(0), Utils.webpToByteArray(R.drawable.pera, this)));
            items.add(new Item("Platano", categoryManager.get(0), Utils.webpToByteArray(R.drawable.platano, this)));

            items.add(new Item("Lechuga", categoryManager.get(1), Utils.webpToByteArray(R.drawable.lechuga, this)));
            items.add(new Item("Tomate", categoryManager.get(1), Utils.webpToByteArray(R.drawable.tomate, this)));
            items.add(new Item("Cebolla", categoryManager.get(1), Utils.webpToByteArray(R.drawable.cebolla, this)));

            items.add(new Item("Pollo", categoryManager.get(2), Utils.webpToByteArray(R.drawable.pollo, this)));
            items.add(new Item("Ternera", categoryManager.get(2), Utils.webpToByteArray(R.drawable.ternera, this)));
            items.add(new Item("Cerdo", categoryManager.get(2), Utils.webpToByteArray(R.drawable.cerdo, this)));

            items.add(new Item("Salmon", categoryManager.get(3), Utils.webpToByteArray(R.drawable.salmon, this)));
            items.add(new Item("Bacalao", categoryManager.get(3), Utils.webpToByteArray(R.drawable.bacalao, this)));
            items.add(new Item("Atún", categoryManager.get(3), Utils.webpToByteArray(R.drawable.atun, this)));

            items.add(new Item("Leche", categoryManager.get(4), Utils.webpToByteArray(R.drawable.leche, this)));
            items.add(new Item("Queso", categoryManager.get(4), Utils.webpToByteArray(R.drawable.queso, this)));
            items.add(new Item("Yogur", categoryManager.get(4), Utils.webpToByteArray(R.drawable.yogur, this)));

            items.add(new Item("Agua", categoryManager.get(5), Utils.webpToByteArray(R.drawable.agua, this)));
            items.add(new Item("Cerveza", categoryManager.get(5), Utils.webpToByteArray(R.drawable.cerveza, this)));
            items.add(new Item("Vino", categoryManager.get(5), Utils.webpToByteArray(R.drawable.vino, this)));

            items.add(new Item("Detergente", categoryManager.get(6), Utils.webpToByteArray(R.drawable.detergente, this)));
            items.add(new Item("Suavizante", categoryManager.get(6), Utils.webpToByteArray(R.drawable.suavizante, this)));
            items.add(new Item("Lejia", categoryManager.get(6), Utils.webpToByteArray(R.drawable.lejia, this)));

            items.add(new Item("Papel higienico", categoryManager.get(7), Utils.webpToByteArray(R.drawable.papel_higienico, this)));
            items.add(new Item("Gel", categoryManager.get(7), Utils.webpToByteArray(R.drawable.gel, this)));
            items.add(new Item("Champu", categoryManager.get(7), Utils.webpToByteArray(R.drawable.champu, this)));

            items.add(new Item("Pan", categoryManager.get(8), Utils.webpToByteArray(R.drawable.pan, this)));
            items.add(new Item("Huevos", categoryManager.get(8), Utils.webpToByteArray(R.drawable.huevos, this)));
            items.add(new Item("Arroz", categoryManager.get(8), Utils.webpToByteArray(R.drawable.arroz, this)));

            itemManager.addAll(items);

            System.out.println("Initialized");
        }
    }

    public void init() {
        System.out.println("Init run");
        helper = ShoppingListSQLiteHelper.getInstance(this);
        db = helper.getWritableDatabase();
        fcvContainer = findViewById(R.id.fcvContainer);

        manager = new ShoppingListManager(db);
        itemManager = new ItemManager(db);
        categoryManager = new CategoryManager(db);
    }

    @Override
    public void onShoppingListClick(ShoppingList shoppingList) {
        System.out.println("Received: " + shoppingList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvContainer, new ShoppingListDetailFragment(shoppingList))
                .addToBackStack("shoppingListDetailFragment")
                .commit();

    }

    @Override
    public ArrayList<ShoppingList> getShoppingLists() {
        if (manager == null) {
            init();
        }
        return manager;
    }

    @Override
    public void onShoppingListItemClicked(ShoppingList shoppingList, Item item) {
        //TODO: Implementar
    }
}