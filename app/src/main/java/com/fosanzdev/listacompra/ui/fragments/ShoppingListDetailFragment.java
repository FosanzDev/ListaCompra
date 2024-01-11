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
import com.fosanzdev.listacompra.models.Item;
import com.fosanzdev.listacompra.models.ShoppingList;
import com.fosanzdev.listacompra.ui.ShoppingListItemsAdapter;

public class ShoppingListDetailFragment extends Fragment {

    public interface IShoppingListDetailFragmentListener {
        void onShoppingListItemClicked(ShoppingList shoppingList, Item item);
    }

    private IShoppingListDetailFragmentListener listener;
    private ShoppingList shoppingList;
    private Context context;

    public ShoppingListDetailFragment(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shopping_list_detail_fragment, container, false);

        RecyclerView rvItems = v.findViewById(R.id.rvShoppingListItems);
        ShoppingListItemsAdapter adapter = new ShoppingListItemsAdapter(shoppingList.getItems());
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new GridLayoutManager(context, 3));

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        listener = (IShoppingListDetailFragmentListener) context;
    }
}
