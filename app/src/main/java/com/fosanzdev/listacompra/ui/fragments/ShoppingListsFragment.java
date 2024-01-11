package com.fosanzdev.listacompra.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.listacompra.R;
import com.fosanzdev.listacompra.models.ShoppingList;
import com.fosanzdev.listacompra.ui.ShoppingListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShoppingListsFragment extends Fragment {

    public interface IShoppingListFragmentListener {
        ArrayList<ShoppingList> getShoppingLists();
    }

    private ArrayList<ShoppingList> shoppingLists;
    private IShoppingListFragmentListener listener;
    private Context context;

    public ShoppingListsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shopping_list_fragment, container, false);

        // -----------------------------------
        // FLOATING ACTION BUTTON (FAB) SETUP
        // This will create a new ShoppingList
        // -----------------------------------
        FloatingActionButton fabAddShoppingList = v.findViewById(R.id.fabAddShoppingList);
        fabAddShoppingList.setOnClickListener(l -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Nueva lista de la compra");
            EditText etShoppingListName = new EditText(context);
            builder.setView(etShoppingListName);

            builder.setPositiveButton("Crear", (dialog, which) -> {
                String shoppingListName = etShoppingListName.getText().toString();
                ShoppingList shoppingList = new ShoppingList(shoppingListName);
                shoppingLists.add(shoppingList);
            });

            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        RecyclerView rvShoppingLists = v.findViewById(R.id.rvShoppingLists);
        rvShoppingLists.setAdapter(new ShoppingListAdapter(shoppingLists, context));
        rvShoppingLists.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.listener = (IShoppingListFragmentListener) context;
        this.shoppingLists = listener.getShoppingLists();
    }
}
