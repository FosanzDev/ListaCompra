package com.fosanzdev.listacompra.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.listacompra.R;
import com.fosanzdev.listacompra.models.ShoppingList;
import com.fosanzdev.listacompra.ui.fragments.ShoppingListsFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>{

    private final ArrayList<ShoppingList> shoppingLists;
    private final Context context;

    public ShoppingListAdapter(ArrayList<ShoppingList> shoppingLists, Context context) {
        this.shoppingLists = shoppingLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ShoppingListAdapter.ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingListViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ShoppingListViewHolder holder, int position) {
        holder.bind(shoppingLists.get(position));
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public interface IOnShoppingListClickListener{
            void onShoppingListClick(ShoppingList shoppingList);
        }

        ShoppingList shoppingList;
        Context context;
        TextView tvShoppingListName;
        TextView tvTimeStamp;
        TextView tvProductCount;

        public ShoppingListViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvShoppingListName = itemView.findViewById(R.id.tvShoppingListName);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            tvProductCount = itemView.findViewById(R.id.tvProductCount);
            itemView.setOnClickListener(this);
        }

        public void bind(ShoppingList shoppingList){
            this.shoppingList = shoppingList;
            tvShoppingListName.setText(shoppingList.getNombre());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(shoppingList.getDate());
            tvTimeStamp.setText(date);
            tvProductCount.setText(String.valueOf(shoppingList.getItems().size()));
        }

        @Override
        public void onClick(View v) {
            IOnShoppingListClickListener listener = (IOnShoppingListClickListener) context;
            listener.onShoppingListClick(shoppingList);
        }
    }
}
