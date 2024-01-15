package com.fosanzdev.listacompra.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.listacompra.R;
import com.fosanzdev.listacompra.controllers.ItemManager;
import com.fosanzdev.listacompra.controllers.ShoppingListManager;
import com.fosanzdev.listacompra.models.Item;
import com.fosanzdev.listacompra.models.ShoppingList;
import com.fosanzdev.listacompra.ui.GridItemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class ShoppingListDetailFragment extends Fragment {
    private final ShoppingList shoppingList;
    private Context context;
    private ShoppingListManager manager;
    private GridItemAdapter adapter;
    private ItemManager itemManager;

    public ShoppingListDetailFragment(ShoppingList shoppingList, ShoppingListManager manager, ItemManager itemManager) {
        this.shoppingList = shoppingList;
        this.manager = manager;
        this.itemManager = itemManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shopping_list_detail_fragment, container, false);

        RecyclerView rvItems = v.findViewById(R.id.rvShoppingListItems);
        adapter = new GridItemAdapter(shoppingList.getItems(), (GridItemAdapter.ItemAdapter.IOnItemClickedListener) context);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new GridLayoutManager(context, 3));

        FloatingActionButton fabAddItem = v.findViewById(R.id.fabAddItem);
        fabAddItem.setOnClickListener(l -> {
            //Add a random item just for testing purposes
            System.out.println("Clicked");


            manager.addItem(shoppingList, itemManager.get(shoppingList.getItems().size()));
            adapter.notifyDataSetChanged();
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public ShoppingList getRelatedShoppingList() {
        return shoppingList;
    }

    public void updateAdapter(){
        adapter.notifyDataSetChanged();
    }
}
