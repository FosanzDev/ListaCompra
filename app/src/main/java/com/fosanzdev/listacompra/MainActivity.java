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
import com.fosanzdev.listacompra.models.ItemViewFittable;
import com.fosanzdev.listacompra.models.ShoppingList;
import com.fosanzdev.listacompra.ui.GridItemAdapter;
import com.fosanzdev.listacompra.ui.ShoppingListAdapter;
import com.fosanzdev.listacompra.ui.fragments.ShoppingListDetailFragment;
import com.fosanzdev.listacompra.ui.fragments.ShoppingListsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ShoppingListsFragment.IShoppingListFragmentListener, GridItemAdapter.ItemAdapter.IOnItemClickedListener, ShoppingListAdapter.ShoppingListViewHolder.IOnShoppingListClickListener {

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

        if (manager == null)
            init();
    }

    public void init() {
        helper = ShoppingListSQLiteHelper.getInstance(this);
        db = helper.getWritableDatabase();
        fcvContainer = findViewById(R.id.fcvContainer);

        manager = new ShoppingListManager(db);
        itemManager = new ItemManager(db);
        categoryManager = new CategoryManager(db);

        if (!ShoppingListSQLiteHelper.initialized) {
            ArrayList<Category> categories = new ArrayList<>();
            categories.add(new Category("Frutas", Utils.webpToBitmap(R.drawable.frutas, this)));
            categories.add(new Category("Verduras", Utils.webpToBitmap(R.drawable.verduras, this)));
            categories.add(new Category("Carnes", Utils.webpToBitmap(R.drawable.carnes, this)));
            categories.add(new Category("Pescados", Utils.webpToBitmap(R.drawable.pescados, this)));
            categories.add(new Category("Lácteos", Utils.webpToBitmap(R.drawable.lacteos, this)));
            categories.add(new Category("Bebidas", Utils.webpToBitmap(R.drawable.bebidas, this)));
            categories.add(new Category("Droguería", Utils.webpToBitmap(R.drawable.drogueria, this)));
            categories.add(new Category("Higiene", Utils.webpToBitmap(R.drawable.higiene, this)));
            categories.add(new Category("Otros", Utils.webpToBitmap(R.drawable.huevos, this)));
            categoryManager.addAll(categories);

            ArrayList<Item> items = new ArrayList<>();
            items.add(new Item("Manzana", categoryManager.get(0), Utils.webpToBitmap(R.drawable.manzana,this)));
            items.add(new Item("Pera", categoryManager.get(0), Utils.webpToBitmap(R.drawable.pera, this)));
            items.add(new Item("Platano", categoryManager.get(0), Utils.webpToBitmap(R.drawable.platano, this)));

            items.add(new Item("Lechuga", categoryManager.get(1), Utils.webpToBitmap(R.drawable.lechuga, this)));
            items.add(new Item("Tomate", categoryManager.get(1), Utils.webpToBitmap(R.drawable.tomate, this)));
            items.add(new Item("Cebolla", categoryManager.get(1), Utils.webpToBitmap(R.drawable.cebolla, this)));

            items.add(new Item("Pollo", categoryManager.get(2), Utils.webpToBitmap(R.drawable.pollo, this)));
            items.add(new Item("Ternera", categoryManager.get(2), Utils.webpToBitmap(R.drawable.ternera, this)));
            items.add(new Item("Cerdo", categoryManager.get(2), Utils.webpToBitmap(R.drawable.cerdo, this)));

            items.add(new Item("Salmon", categoryManager.get(3), Utils.webpToBitmap(R.drawable.salmon, this)));
            items.add(new Item("Bacalao", categoryManager.get(3), Utils.webpToBitmap(R.drawable.bacalao, this)));
            items.add(new Item("Atún", categoryManager.get(3), Utils.webpToBitmap(R.drawable.atun, this)));

            items.add(new Item("Leche", categoryManager.get(4), Utils.webpToBitmap(R.drawable.leche, this)));
            items.add(new Item("Queso", categoryManager.get(4), Utils.webpToBitmap(R.drawable.queso, this)));
            items.add(new Item("Yogur", categoryManager.get(4), Utils.webpToBitmap(R.drawable.yogur, this)));

            items.add(new Item("Agua", categoryManager.get(5), Utils.webpToBitmap(R.drawable.agua, this)));
            items.add(new Item("Cerveza", categoryManager.get(5), Utils.webpToBitmap(R.drawable.cerveza, this)));
            items.add(new Item("Vino", categoryManager.get(5), Utils.webpToBitmap(R.drawable.vino, this)));

            items.add(new Item("Detergente", categoryManager.get(6), Utils.webpToBitmap(R.drawable.detergente, this)));
            items.add(new Item("Suavizante", categoryManager.get(6), Utils.webpToBitmap(R.drawable.suavizante, this)));
            items.add(new Item("Lejia", categoryManager.get(6), Utils.webpToBitmap(R.drawable.lejia, this)));

            items.add(new Item("Papel higienico", categoryManager.get(7), Utils.webpToBitmap(R.drawable.papel_higienico, this)));
            items.add(new Item("Gel", categoryManager.get(7), Utils.webpToBitmap(R.drawable.gel, this)));
            items.add(new Item("Champu", categoryManager.get(7), Utils.webpToBitmap(R.drawable.champu, this)));

            items.add(new Item("Pan", categoryManager.get(8), Utils.webpToBitmap(R.drawable.pan, this)));
            items.add(new Item("Huevos", categoryManager.get(8), Utils.webpToBitmap(R.drawable.huevos, this)));
            items.add(new Item("Arroz", categoryManager.get(8), Utils.webpToBitmap(R.drawable.arroz, this)));

            itemManager.addAll(items);
        }
    }

    @Override
    public void onShoppingListClick(ShoppingList shoppingList) {
        System.out.println("Received: " + shoppingList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvContainer, new ShoppingListDetailFragment(shoppingList, manager, itemManager))
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
    public void onItemClicked(ItemViewFittable item) {
        if (item instanceof Item){
            //Get the fragment and add the item to the shopping list
            ShoppingListDetailFragment fragment = (ShoppingListDetailFragment) getSupportFragmentManager().getFragments().get(0);
            ShoppingList shoppingList = fragment.getRelatedShoppingList();
            manager.removeItem(shoppingList, (Item) item);
            fragment.updateAdapter();
        } else if (item instanceof Category){
        }
    }
}